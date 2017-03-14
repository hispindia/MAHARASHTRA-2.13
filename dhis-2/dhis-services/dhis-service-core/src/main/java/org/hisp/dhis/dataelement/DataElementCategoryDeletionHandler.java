package org.hisp.dhis.dataelement;

/*
 * Copyright (c) 2004-2013, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.dhis.concept.Concept;
import org.hisp.dhis.concept.ConceptService;
import org.hisp.dhis.system.deletion.DeletionHandler;

import java.util.Set;

/**
 * @author Dang Duy Hieu
 */
public class DataElementCategoryDeletionHandler
    extends DeletionHandler
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private DataElementCategoryService categoryService;

    public void setCategoryService( DataElementCategoryService categoryService )
    {
        this.categoryService = categoryService;
    }

    private ConceptService conceptService;

    public void setConceptService( ConceptService conceptService )
    {
        this.conceptService = conceptService;
    }

    // -------------------------------------------------------------------------
    // DeletionHandler implementation
    // -------------------------------------------------------------------------

    @Override
    public String getClassName()
    {
        return DataElementCategory.class.getSimpleName();
    }

    @Override
    public String allowDeleteConcept( Concept concept )
    {
        for ( DataElementCategory category : categoryService.getAllDataElementCategories() )
        {
            Concept categoryConcept = category.getConcept();

            if ( categoryConcept != null )
            {
                if ( categoryConcept.equals( concept ) )
                {
                    return category.getName();
                }
            }
        }

        return null;
    }

    @Override
    public void deleteConcept( Concept concept )
    {
        Concept conceptDefault = conceptService.getConceptByName( Concept.DEFAULT_CONCEPT_NAME );

        for ( DataElementCategory category : categoryService.getAllDataElementCategories() )
        {
            Concept categoryConcept = category.getConcept();

            if ( categoryConcept != null )
            {
                if ( categoryConcept.equals( concept ) )
                {
                    category.setConcept( conceptDefault );
                    categoryService.updateDataElementCategory( category );
                }
            }
        }
    }

    @Override
    public void deleteDataElementCategoryOption( DataElementCategoryOption categoryOption )
    {
        Set<DataElementCategory> categories = categoryOption.getCategories();

        for ( DataElementCategory category : categories )
        {
            category.removeDataElementCategoryOption( categoryOption );
            categoryService.updateDataElementCategory( category );
        }
    }
}
