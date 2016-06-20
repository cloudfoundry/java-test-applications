/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.java.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/product")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class ProductService {

    @PersistenceContext(unitName = "store", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Path("/add")
    @POST
    public Response addProduct(@FormParam(value = "name") String name) throws URISyntaxException {
        if (name == null || name.equals("")) {
            name = "undefined";
        }
        final Product product = new Product();
        product.setProductName(name);
        entityManager.persist(product);
        return Response.temporaryRedirect(new URI("/products")).build();
    }

    @SuppressWarnings("unchecked")
    @Path("/list")
    @GET
    public List<Product> listAllProducts() {
        return entityManager.createNamedQuery("AllProducts").getResultList();
    }

    @Path("/delete/{id}")
    @POST
    public Response deleteProduct(@PathParam(value = "id") long id) throws URISyntaxException {
        final Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
        return Response.temporaryRedirect(new URI("/products")).build();
    }
}