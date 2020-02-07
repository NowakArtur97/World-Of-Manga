INSERT INTO `user`(`username`, `password`, `email`, `is_enabled`) values 
('user', '$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2', 'user@email.com', '1'),
('admin', '$2a$10$21qh5sabaXLO/IVFOmNSbOPjLTSpUdjvpD7kiIx3Ckr9NovR7CRZO', 'admin@email.com', '1');

INSERT INTO `role` (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1, 1), (2, 1), (2, 2);