package org.hisp.dhis.dataadmin.action.locale;

/*
 * Copyright (c) 2004-2009, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the HISP project nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
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

import java.util.Map;

import org.hisp.dhis.i18n.I18nLocaleService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

/**
 * @author James Chang
 * @version $Id$
 */
public class ShowAddLocaleFormAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependency
    // -------------------------------------------------------------------------

    @Autowired
    private I18nLocaleService localeService;

    // -------------------------------------------------------------------------
    // Output
    // -------------------------------------------------------------------------

    private Map<String, String> availableLanguages;

    public Map<String, String> getAvailableLanguages()
    {
        return availableLanguages;
    }

    private Map<String, String> availableCountries;

    public Map<String, String> getAvailableCountries()
    {
        return availableCountries;
    }
        
    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

    public String execute()
    {
    	availableLanguages = localeService.getAvailableLanguages();
    	availableCountries = localeService.getAvailableCountries();
    	
        return SUCCESS;
    }
}
