package org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs2_2;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Person;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.v1_0.RestTestConstants2_2;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Tests CRUD operations for {@link Person}s via web service calls
 */
public class PersonController2_2Test extends MainResourceControllerTest {

    private PersonService service;

    @Override
    public String getURI() {
        return "person";
    }

    @Override
    public String getUuid() {
        return RestTestConstants2_2.PERSON_UUID;
    }

    @Override
    public long getAllCount() {
        return 0;
    }

    @Before
    public void before() {
        this.service = Context.getPersonService();
    }

    @Override
    @Test(expected = ResourceDoesNotSupportOperationException.class)
    public void shouldGetAll() throws Exception {
        super.shouldGetAll();
    }

    @Test
    public void shouldEditAPerson() throws Exception {
        Person person = service.getPersonByUuid(getUuid());
        assertFalse("F".equals(person.getGender()));
        assertFalse(person.isDead());
        assertNull(person.getCauseOfDeathNonCoded());
        String json = "{\"gender\":\"F\",\"dead\":true, \"causeOfDeathNonCoded\":\"Eating_Banana\"}";
        SimpleObject response = deserialize(handle(newPostRequest(getURI() + "/" + getUuid(), json)));
        assertNotNull(response);
        Object responsePersonContents = PropertyUtils.getProperty(response, "person");
        assertNotNull(responsePersonContents);
        assertTrue("F".equals(PropertyUtils.getProperty(responsePersonContents, "gender").toString()));
        assertEquals("F", person.getGender());
        assertTrue(person.isDead());
        assertNotNull(person.getCauseOfDeathNonCoded());
    }
}
