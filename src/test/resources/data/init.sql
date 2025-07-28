TRUNCATE TABLE book, author;
ALTER SEQUENCE author_seq RESTART WITH 1;
ALTER SEQUENCE public.book_seq RESTART WITH 1;

INSERT INTO author (id, firstname, lastname, created_date, modified_date)
  VALUES ( nextval('author_seq'), 'JRR', 'Tolkien', '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');

INSERT INTO book(id, label, summary, isbn, author_id, created_date, modified_date)
  VALUES (nextval('book_seq'), 'La Communauté de l''Anneau', 'Aux temps reculés qu''évoque le récit, la Terre est peuplée d''innombrables créatures étranges. Les Hobbits, apparentés à l''Homme, mais proches également des Elfes et des Nains, vivent en paix au nord-ouest de l''Ancien Monde, dans la Comté. Paix précaire et menacée, cependant, depuis que Bilbon Sacquet a dérobé au monstre Gollum l''anneau de Puissance jadis forgé par Sauron de Mordor. Car cet anneau est doté d''un pouvoir immense et maléfique. Il permet à son détenteur de se rendre invisible et lui confère une autorité sans limites sur les possesseurs des autres anneaux. Bref, il fait de lui le Maître du Monde. C''est pourquoi Sauron s''est juré de reconquérir l''anneau par tous les moyens. Déjà ses Cavaliers Noirs rôdent aux frontières de la Comté.', '978-2070612888', 1, '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');
INSERT INTO book(id, label, summary, isbn, author_id, created_date, modified_date)
  VALUES (nextval('book_seq'), 'Les Deux Tours', 'Dispersée dans les terres de l''Ouest, la Communauté de l''Anneau affronte les périls de la guerre, tandis que Frodon, accompagné du fidèle Sam, poursuit une quête presque désespérée : détruire l''Anneau, unique en le jetant dans les crevasses d''Oradruir, a Montagne du destin. Mais aux frontières du royaume de Mordor, une mystérieuse créature les épie... Pour les perdre ou pour les sauver ?', '978-2267046892', 1, '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');
INSERT INTO book(id, label, summary, isbn, author_id, created_date, modified_date)
  VALUES (nextval('book_seq'), 'Le Retour du Roi', 'Tandis que le continent se couvre de ténèbres, annonçant pour le peuple des Hobbits l''aube d''une nouvelle ère, Frodon poursuit son entreprise : il lui faut à tout prix atteindre le Mont du Destin. Mais le seigneur des Ténèbres mobilise ses troupes. L''ennemi est partout et Frodon doit s''engager dans un dangereux périple à travers le Pays Noir... Les derniers combats de la guerre de l''Anneau s''achèvent dans un fracas d''apocalypse.', '978-2267046908', 1, '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');



INSERT INTO author (id, firstname, lastname, created_date, modified_date)
  VALUES ( nextval('author_seq'),'Robert C.', 'Martin', '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');

INSERT INTO book(id, label, summary, isbn, author_id, created_date, modified_date)
  VALUES (nextval('book_seq'), 'Clean Code', 'Even bad code can function. But if code isn’t clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn’t have to be that way.', '978-0132350884', 2, '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');
INSERT INTO book(id, label, summary, isbn, author_id, created_date, modified_date)
  VALUES (nextval('book_seq'), 'Clean Architecture', 'By applying universal rules of software architecture, you can dramatically improve developer productivity throughout the life of any software system. Now, building upon the success of his best-selling books Clean Code and The Clean Coder, legendary software craftsman Robert C. Martin (“Uncle Bob”) reveals those rules and helps you apply them.', '978-0134494166', 2, '2025-07-25 10:00:00Z', '2025-07-25 10:00:00Z');


TRUNCATE TABLE book_aud, author_aud, revinfo;
--ALTER SEQUENCE revinfo_seq RESTART WITH 1;