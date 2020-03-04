INSERT INTO world_of_manga.user(username, password, email, is_enabled) values 
('user', '$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2', 'user@email.com', '1'),
('admin', '$2a$10$21qh5sabaXLO/IVFOmNSbOPjLTSpUdjvpD7kiIx3Ckr9NovR7CRZO', 'admin@email.com', '1');

INSERT INTO world_of_manga.role (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO world_of_manga.user_role(user_id, role_id) VALUES (1, 1), (2, 1), (2, 2);

INSERT INTO world_of_manga.language (locale) VALUES ('en'), ('pl');

INSERT INTO world_of_manga.genre (genre) VALUES  ('4-koma'), ('Action'), ('Adventure'), ('Comedy'), ('Crime'), ('Demons'), ('Doujinshi'), ('Drama'), ('Ecchi'), ('Fantasy'), ('Food'), ('Harem'), ('Historical'), ('Horror'), ('Isekai'), ('Josei'), ('Kids'), ('Magic'), ('Martial Arts'), ('Mature'), ('Mecha'), ('Military'), ('Music'), ('Mystery'), ('Mystery/Suspense'), ('One Shot'), ('Parody'), ('Police'), ('Psychological'), ('Romance'), ('School Life'), ('Sci-Fi'), ('Science Fiction'), ('Seinen'), ('Shoujo'), ('Shoujo Ai'), ('Shoujo-ai'), ('Shounen'), ('Shounen Ai'), ('Shounen-ai'), ('Slice of Life'), ('Space'), ('Sports'), ('Super Power'), ('Supernatural'), ('Tragedy'), ('Vampire'), ('Vampires'), ('Webtoons'), ('Yaoi'), ('Yuri');
