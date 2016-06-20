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

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products")
public class ProductInfoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProductService productService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        writer.append("<html><body><center><h2>ejb-application<h2>"
            + "<table border=\"1\" width=\"300\"><tr><td>Products:</td><td>Delete</td></tr>");

        final List<Product> products = productService.listAllProducts();
        for (Product product : products) {
            writer.append("<tr><td>" + product.getProductName() + "</td><td>");
            deleteButtonForm(response, product.getId());
            writer.append("</td></tr>");
        }
        writer.append("</table>");
        addForm(response);
        writer.append("</center></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void addForm(HttpServletResponse response) throws IOException {
        response.getWriter().append(
            "<p><form action=\"product\\add\" method=\"post\">Product:<input type=\"text\" name=\"name\">"
                + "&nbsp;<input type=\"submit\" value=\"Add\"></form></p>");
    }

    private void deleteButtonForm(HttpServletResponse response, long productId) throws IOException {
        response.getWriter().append(
            "<form action=\"product\\delete\\" + productId + "\" method=\"post\"><button>Delete</button></form>");
    }

}
