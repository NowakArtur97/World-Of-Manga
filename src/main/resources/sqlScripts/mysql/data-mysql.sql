INSERT INTO `user`(`username`, `password`, `email`, `is_enabled`) VALUES
('user', '$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2', 'user@email.com', 1),
('admin', '$2a$10$21qh5sabaXLO/IVFOmNSbOPjLTSpUdjvpD7kiIx3Ckr9NovR7CRZO', 'admin@email.com', 1);

INSERT INTO `role` (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1, 1), (2, 1), (2, 2);

INSERT INTO `language` (`locale`) VALUES ('en'), ('pl');

INSERT INTO `genre` (`en_translation`, `pl_translation`)
VALUES  ('4-koma', '4-koma'), ('Action', 'Akcja'), ('Adventure', 'Przygoda'), ('Comedy', 'Komedia'), ('Crime', 'Kryminał'), ('Demons', 'Demony'), ('Doujinshi', 'Doujinshi'), ('Drama', 'Dramat'), ('Ecchi', 'Ecchi'), ('Fantasy', 'Fantastyczne'), ('Food', 'Jedzenie'), ('Harem', 'Harem'), ('Historical', 'Historyczne'), ('Horror', 'Horror'), ('Isekai', 'Isekai'), ('Josei', 'Josei'), ('Kids', 'Dla Dzieci'), ('Magic', 'Magia'), ('Martial Arts', 'Sztuki Walki'), ('Mature', 'Dla Dorosłych'), ('Mecha', 'Roboty'), ('Military', 'Militarne'), ('Music', 'Muzyka'), ('Mystery', 'Tajemnica'), ('One Shot', 'One Shot'), ('Parody', 'Parodia'), ('Police', 'Policja'), ('Psychological', 'Psychologiczne'), ('Romance', 'Romans'), ('School Life', 'Szkolne życie'), ('Sci-Fi', 'Sci-Fi'), ('Science Fiction', 'Fantastyka naukowa'), ('Seinen', 'Seinen'), ('Shoujo', 'Shoujo'), ('Shoujo Ai', 'Shoujo Ai'), ('Shoujo-ai', 'Shoujo-ai'), ('Shounen', 'Shounen'), ('Shounen Ai', 'Shounen Ai'), ('Shounen-ai', 'Shounen-ai'), ('Slice of Life', 'Okruchy życia'), ('Space', 'Kosmos'), ('Sports', 'Sport'), ('Super Power', 'Super Moce'), ('Supernatural', 'Nadprzyrodzone'), ('Tragedy', 'Tragedia'), ('Vampire', 'Wapir'), ('Vampires', 'Wampiry'), ('Webtoons', 'Webtoon'), ('Yaoi', 'Yaoi'), ('Yuri', 'Yuri');
