INSERT INTO `user`(`username`, `password`, `email`, `is_enabled`) VALUES 
('user', '$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2', 'user@email.com', '1'),
('admin', '$2a$10$21qh5sabaXLO/IVFOmNSbOPjLTSpUdjvpD7kiIx3Ckr9NovR7CRZO', 'admin@email.com', '1');

INSERT INTO `role` (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1, 1), (2, 1), (2, 2);

INSERT INTO `language` (`locale`) VALUES ('en'), ('pl');

INSERT INTO `genre` (`genre`, `language_id`) VALUES  ('4-koma', 1), ('Action', 1), ('Adventure', 1), ('Comedy', 1), ('Crime', 1), ('Demons', 1), ('Doujinshi', 1), ('Drama', 1), ('Ecchi', 1), ('Fantasy', 1), ('Food', 1), ('Harem', 1), ('Historical', 1), ('Horror', 1), ('Isekai', 1), ('Josei', 1), ('Kids', 1), ('Magic', 1), ('Martial Arts', 1), ('Mature', 1), ('Mecha', 1), ('Military', 1), ('Music', 1), ('Mystery', 1), ('Mystery/Suspense', 1), ('One Shot', 1), ('Parody', 1), ('Police', 1), ('Psychological', 1), ('Romance', 1), ('School Life', 1), ('Sci-Fi', 1), ('Science Fiction', 1), ('Seinen', 1), ('Shoujo', 1), ('Shoujo Ai', 1), ('Shoujo-ai', 1), ('Shounen', 1), ('Shounen Ai', 1), ('Shounen-ai', 1), ('Slice of Life', 1), ('Space', 1), ('Sports', 1), ('Super Power', 1), ('Supernatural', 1), ('Tragedy', 1), ('Vampire', 1), ('Vampires', 1), ('Webtoons', 1), ('Yaoi', 1), ('Yuri', 1);
