package org.hisp.dhis.importexport.importer;

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

import org.amplecode.quick.BatchHandler;
import org.hisp.dhis.dataelement.DataElementGroup;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.importexport.GroupMemberType;
import org.hisp.dhis.importexport.ImportParams;
import org.hisp.dhis.importexport.Importer;
import org.hisp.dhis.importexport.mapping.NameMappingUtil;

/**
 * @author Lars Helge Overland
 * @version $Id: AbstractDataElementGroupConverter.java 4646 2008-02-26 14:54:29Z larshelg $
 */
public class DataElementGroupImporter
    extends AbstractImporter<DataElementGroup> implements Importer<DataElementGroup>
{
    protected DataElementService dataElementService;

    public DataElementGroupImporter()
    {
    }
    
    public DataElementGroupImporter( BatchHandler<DataElementGroup> batchHandler, DataElementService dataElementService )
    {
        this.batchHandler = batchHandler;
        this.dataElementService = dataElementService;
    }
    
    @Override
    public void importObject( DataElementGroup object, ImportParams params )
    {
        NameMappingUtil.addDataElementGroupMapping( object.getId(), object.getName() );
        
        read( object, GroupMemberType.NONE, params );
    }

    @Override
    protected void importUnique( DataElementGroup object )
    {
        batchHandler.addObject( object );      
    }

    @Override
    protected void importMatching( DataElementGroup object, DataElementGroup match )
    {
        match.setName( object.getName() );
        
        dataElementService.updateDataElementGroup( match );
    }

    @Override
    protected DataElementGroup getMatching( DataElementGroup object )
    {
        return dataElementService.getDataElementGroupByName( object.getName() );
    }

    @Override
    protected boolean isIdentical( DataElementGroup object, DataElementGroup existing )
    {
        return object.getName().equals( existing.getName() );
    }
}
