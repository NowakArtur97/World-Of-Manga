INSERT INTO world_of_manga.user("username", "password", "email", "is_enabled") values 
('user', '$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2', 'user@email.com', '1'),
('admin', '$2a$10$21qh5sabaXLO/IVFOmNSbOPjLTSpUdjvpD7kiIx3Ckr9NovR7CRZO', 'admin@email.com', '1');

INSERT INTO world_of_manga.role (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO world_of_manga.user_role("user_id", "role_id") VALUES (1, 1), (2, 1), (2, 2);

INSERT INTO world_of_manga.language ("locale") VALUES ('en'), ('pl');

INSERT INTO world_of_manga.genre ("genre") VALUES  ('4-koma'), ('Action'), ('Adventure'), ('Comedy'), ('Crime'), ('Demons'), ('Doujinshi'), ('Drama'), ('Ecchi'), ('Fantasy'), ('Food'), ('Harem'), ('Historical'), ('Horror'), ('Isekai'), ('Josei'), ('Kids'), ('Magic'), ('Martial Arts'), ('Mature'), ('Mecha'), ('Military'), ('Music'), ('Mystery'), ('Mystery/Suspense'), ('One Shot'), ('Parody'), ('Police'), ('Psychological'), ('Romance'), ('School Life'), ('Sci-Fi'), ('Science Fiction'), ('Seinen'), ('Shoujo'), ('Shoujo Ai'), ('Shoujo-ai'), ('Shounen'), ('Shounen Ai'), ('Shounen-ai'), ('Slice of Life'), ('Space'), ('Sports'), ('Super Power'), ('Supernatural'), ('Tragedy'), ('Vampire'), ('Vampires'), ('Webtoons'), ('Yaoi'), ('Yuri');

INSERT INTO world_of_manga.genre_language ("language_id", "genre_id") 
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20), (1, 21), (1, 22), (1, 23), (1, 24), (1, 25), (1, 26), (1, 27), (1, 28), (1, 29), (1, 30), (1, 31), (1, 32), (1, 33), (1, 34), (1, 35), (1, 36), (1, 37), (1, 38), (1, 39), (1, 40), (1, 41), (1, 42), (1, 43), (1, 44), (1, 45), (1, 46), (1, 47), (1, 48), (1, 49), (1, 50), (1, 51);
