package com.zenika.hibernate.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.hibernate.AbstractSpringBootTest;
import com.zenika.hibernate.infrastructure.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryControllerTest extends AbstractSpringBootTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @SneakyThrows
    @Order(1)
    void shouldGetAuthorTolkien() {
        // GIVEN

        // WHEN
        MvcTestResult exchange = mockMvcTester.get()
                .uri("/library/author/{id}", TOLKIEN_ID)
                .exchange();

        // THEN
        logBodyAsJson(exchange.getResponse().getContentAsString());

        assertThat(exchange)
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo(
                        """
                                {
                                  "id": 1,
                                  "lastname": "Tolkien",
                                  "firstname": "JRR"
                                }
                                """
                );

    }

    @Test
    @Order(2)
    @Transactional() // With this the update will be rollback after the test
    void shouldDeleteAuthor() {
        // GIVEN

        // WHEN
        MvcTestResult exchange = mockMvcTester.delete()
                .uri("/library/author/{id}", TOLKIEN_ID)
                .exchange();

        // THEN
        assertThat(exchange)
                .hasStatusOk();
        assertThat(authorRepository.findById(TOLKIEN_ID)).isEmpty();
    }

    @Test
    @SneakyThrows
    @Order(3)
    void shouldGetAuthors() {
        // GIVEN

        // WHEN
        MvcTestResult exchange = mockMvcTester.get()
                .uri("/library/author")
                .exchange();

        // THEN
        logBodyAsJson(exchange.getResponse().getContentAsString());

        assertThat(exchange)
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo(
                        """
                                [ {
                                  "id" : 1,
                                  "firstname" : "JRR",
                                  "lastname" : "Tolkien"
                                }, {
                                  "id" : 2,
                                  "firstname" : "Robert C.",
                                  "lastname" : "Martin"
                                } ]
                                """
                );


    }

    @Test
    @SneakyThrows
    void shouldGetBook() {
        // GIVEN

        // WHEN
        MvcTestResult exchange = mockMvcTester.get()
                .uri("/library/book/{id}", 1)
                .exchange();

        // THEN
        logBodyAsJson(exchange.getResponse().getContentAsString());

        assertThat(exchange)
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo(
                        """
                                {
                                  "id" : 1,
                                  "isbn" : "978-2070612888",
                                  "label":"La Communauté de l'Anneau",
                                  "summary" : "Aux temps reculés qu'évoque le récit, la Terre est peuplée d'innombrables créatures étranges. Les Hobbits, apparentés à l'Homme, mais proches également des Elfes et des Nains, vivent en paix au nord-ouest de l'Ancien Monde, dans la Comté. Paix précaire et menacée, cependant, depuis que Bilbon Sacquet a dérobé au monstre Gollum l'anneau de Puissance jadis forgé par Sauron de Mordor. Car cet anneau est doté d'un pouvoir immense et maléfique. Il permet à son détenteur de se rendre invisible et lui confère une autorité sans limites sur les possesseurs des autres anneaux. Bref, il fait de lui le Maître du Monde. C'est pourquoi Sauron s'est juré de reconquérir l'anneau par tous les moyens. Déjà ses Cavaliers Noirs rôdent aux frontières de la Comté."
                                }
                                """
                );
    }

    @Test
    @SneakyThrows
    void shouldGetBookWithAuthor() {
        // GIVEN

        // WHEN
        MvcTestResult exchange = mockMvcTester.get()
                .uri("/library/bookWithAuthor/{id}", 1)
                .exchange();

        // THEN
        logBodyAsJson(exchange.getResponse().getContentAsString());

        assertThat(exchange)
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo(
                        """
                                {
                                  "id" : 1,
                                  "label" : "La Communauté de l'Anneau",
                                  "isbn" : "978-2070612888",
                                  "summary" : "Aux temps reculés qu'évoque le récit, la Terre est peuplée d'innombrables créatures étranges. Les Hobbits, apparentés à l'Homme, mais proches également des Elfes et des Nains, vivent en paix au nord-ouest de l'Ancien Monde, dans la Comté. Paix précaire et menacée, cependant, depuis que Bilbon Sacquet a dérobé au monstre Gollum l'anneau de Puissance jadis forgé par Sauron de Mordor. Car cet anneau est doté d'un pouvoir immense et maléfique. Il permet à son détenteur de se rendre invisible et lui confère une autorité sans limites sur les possesseurs des autres anneaux. Bref, il fait de lui le Maître du Monde. C'est pourquoi Sauron s'est juré de reconquérir l'anneau par tous les moyens. Déjà ses Cavaliers Noirs rôdent aux frontières de la Comté.",
                                  "author" : {
                                      "id" : 1,
                                      "firstname" : "JRR",
                                      "lastname" : "Tolkien"
                                    }
                                }
                                """
                );
        assertThat(getQueryCount()).withFailMessage("Must execute only one query. But found %s", getQueryCount()).isEqualTo(1);
    }

    @Test
    @SneakyThrows
    void shouldPageAndSortBooks() {
        // GIVEN

        // WHEN
        MvcTestResult exchange = mockMvcTester.get()
                .uri("/library/all")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .queryParam("sort", "label,ASC")
                .queryParam("sort", "isbn,DESC")
                .exchange();

        // THEN
        logBodyAsJson(exchange.getResponse().getContentAsString());
        assertThat(exchange).hasStatusOk();

        assertThat(getQueryCount()).withFailMessage("Must execute only two query. But found %s", getQueryCount()).isEqualTo(2);
    }

    @SneakyThrows
    private void logBodyAsJson(String value) {
        JsonNode jsonNode = objectMapper.readValue(value, JsonNode.class);
        log.info("Body:\n{}",
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode)
        );
    }

}