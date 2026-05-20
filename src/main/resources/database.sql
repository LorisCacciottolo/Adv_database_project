CREATE TABLE Artist(
    artist_id VARCHAR(50),
    name VARCHAR(100) NOT NULL,
    bio TEXT,
    birthYear INT,
    contactEmail VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    city VARCHAR(100),
    website VARCHAR(255),
    socialMedia VARCHAR(255),
    isActive BOOLEAN DEFAULT TRUE,
    PRIMARY KEY(artist_id)
);

CREATE TABLE Artwork(
    artwork_id VARCHAR(50),
    title VARCHAR(100) NOT NULL,
    creationYear INT,
    type VARCHAR(50),
    medium VARCHAR(50),
    dimensions VARCHAR(50),
    price DECIMAL(10,2),
    status VARCHAR(50),
    artist_id VARCHAR(50) NOT NULL,
    PRIMARY KEY(artwork_id),
    FOREIGN KEY(artist_id) REFERENCES Artist(artist_id) ON DELETE CASCADE
);

CREATE TABLE Workshop(
    workshop_id VARCHAR(50),
    title VARCHAR(100) NOT NULL,
    dateTime DATETIME,
    durationMinutes INT,
    maxParticipants INT,
    price DECIMAL(10,2),
    location VARCHAR(100),
    level VARCHAR(50),
    artist_id VARCHAR(50) NOT NULL,
    PRIMARY KEY(workshop_id),
    FOREIGN KEY(artist_id) REFERENCES Artist(artist_id) ON DELETE CASCADE
);

CREATE TABLE CommunityMember(
    member_id VARCHAR(50),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    birthYear INT,
    phone VARCHAR(20),
    city VARCHAR(100),
    membershipType VARCHAR(50),
    PRIMARY KEY(member_id)
);

CREATE TABLE Organizer(
    organizer_id VARCHAR(50),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    organization VARCHAR(100),
    PRIMARY KEY(organizer_id)
);

CREATE TABLE Discipline(
    discipline_id VARCHAR(50),
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(discipline_id)
);

CREATE TABLE Gallery(
    gallery_id VARCHAR(50),
    rating VARCHAR(50),
    website VARCHAR(255),
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    openingHours VARCHAR(100),
    contactPhone VARCHAR(20),
    organizer_id VARCHAR(50) NOT NULL,
    PRIMARY KEY(gallery_id),
    FOREIGN KEY(organizer_id) REFERENCES Organizer(organizer_id) ON DELETE CASCADE
);

CREATE TABLE Exhibition(
    exhibition_id VARCHAR(50),
    title VARCHAR(100) NOT NULL,
    startDate DATE,
    endDate DATE,
    description TEXT,
    curatorName VARCHAR(100),
    theme VARCHAR(100),
    gallery_id VARCHAR(50) NOT NULL,
    PRIMARY KEY(exhibition_id),
    FOREIGN KEY(gallery_id) REFERENCES Gallery(gallery_id) ON DELETE CASCADE
);

CREATE TABLE Practices(
    artist_id VARCHAR(50),
    discipline_id VARCHAR(50),
    PRIMARY KEY(artist_id, discipline_id),
    FOREIGN KEY(artist_id) REFERENCES Artist(artist_id) ON DELETE CASCADE,
    FOREIGN KEY(discipline_id) REFERENCES Discipline(discipline_id) ON DELETE CASCADE
);

CREATE TABLE Displays(
    artwork_id VARCHAR(50),
    exhibition_id VARCHAR(50),
    PRIMARY KEY(artwork_id, exhibition_id),
    FOREIGN KEY(artwork_id) REFERENCES Artwork(artwork_id) ON DELETE CASCADE,
    FOREIGN KEY(exhibition_id) REFERENCES Exhibition(exhibition_id) ON DELETE CASCADE
);

CREATE TABLE Booking(
    workshop_id VARCHAR(50),
    member_id VARCHAR(50),
    bookingDate DATE,
    paymentStatus VARCHAR(50),
    PRIMARY KEY(workshop_id, member_id),
    FOREIGN KEY(workshop_id) REFERENCES Workshop(workshop_id) ON DELETE CASCADE,
    FOREIGN KEY(member_id) REFERENCES CommunityMember(member_id) ON DELETE CASCADE
);

CREATE TABLE Review(
    artwork_id VARCHAR(50),
    member_id VARCHAR(50),
    rating INT,
    comment TEXT,
    reviewDate DATE,
    PRIMARY KEY(artwork_id, member_id),
    FOREIGN KEY(artwork_id) REFERENCES Artwork(artwork_id) ON DELETE CASCADE,
    FOREIGN KEY(member_id) REFERENCES CommunityMember(member_id) ON DELETE CASCADE
);


-- 1. Insertion de tous les Artistes
INSERT INTO Artist (artist_id, name, bio, birthYear, contactEmail, phone, city, website, socialMedia, isActive) VALUES
('ART-01', 'Léa Dubois', 'Peintre impressionniste passionnée par la nature et les paysages.', 1985, 'lea.dubois@email.com', '0601020304', 'Paris', 'leaduboisart.com', '@lea_art', TRUE),
('ART-02', 'Marc Antoine', 'Photographe urbain capturant l''essence des grandes villes.', 1990, 'marc.a@email.com', '0611223344', 'Lyon', 'marcantoine.fr', '@marc_photo', TRUE),
('ART-03', 'Kevin Chen', 'Créateur d''univers virtuels et d''art génératif.', 1998, 'kevin.c@digitalart.com', '0677889900', 'Bordeaux', 'kevinchen.art', '@kevin_digital', TRUE),
('ART-04', 'Sarah Lambert', 'Sculptrice sur métal travaillant des matériaux recyclés.', 1975, 'sarah.metal@email.com', '0644332211', 'Marseille', 'sarahlambert.fr', '@sarah_sculpt', TRUE);

-- 2. Insertion de toutes les Disciplines
INSERT INTO Discipline (discipline_id, name) VALUES
('DIS-01', 'Peinture'),
('DIS-02', 'Photographie'),
('DIS-03', 'Sculpture'),
('DIS-04', 'Art Numérique'),
('DIS-05', 'Installation');

-- 3. Insertion de toutes les Pratiques (Liaisons)
INSERT INTO Practices (artist_id, discipline_id) VALUES
('ART-01', 'DIS-01'),
('ART-02', 'DIS-02'),
('ART-02', 'DIS-03'),
('ART-03', 'DIS-04'),
('ART-04', 'DIS-03'),
('ART-04', 'DIS-05');

-- 4. Insertion de toutes les Oeuvres (Artworks)
INSERT INTO Artwork (artwork_id, title, creationYear, type, medium, dimensions, price, status, artist_id) VALUES
('AW-01', 'Aube sur la Seine', 2023, 'Peinture', 'Huile sur toile', '100x80 cm', 1200.00, 'Disponible', 'ART-01'),
('AW-02', 'Reflets de Lyon', 2024, 'Photographie', 'Numérique', '60x40 cm', 350.50, 'Vendu', 'ART-02'),
('AW-03', 'Forêt mystique', 2022, 'Peinture', 'Aquarelle', '50x50 cm', 450.00, 'Disponible', 'ART-01'),
('AW-04', 'Matrice 01', 2024, 'Art Numérique', 'Impression 3D', '30x30 cm', 800.00, 'Disponible', 'ART-03'),
('AW-05', 'L''Envol de Fer', 2021, 'Sculpture', 'Acier recyclé', '200x150 cm', 4500.00, 'Exposé', 'ART-04'),
('AW-06', 'Nuit d''orage', 2023, 'Peinture', 'Acrylique', '120x100 cm', 950.00, 'Vendu', 'ART-01');

-- 5. Insertion de tous les Organisateurs
INSERT INTO Organizer (organizer_id, name, email, phone, organization) VALUES
('ORG-01', 'Sophie Martin', 'sophie.m@artevent.com', '0123456789', 'Art Event Association'),
('ORG-02', 'Jean Dupont', 'jean.d@ville-lyon.fr', '0456789123', 'Mairie de Lyon'),
('ORG-03', 'Hugo Vasseur', 'contact@collectif-underground.fr', '0788996655', 'Collectif Underground');

-- 6. Insertion de toutes les Galeries
INSERT INTO Gallery (gallery_id, rating, website, name, address, openingHours, contactPhone, organizer_id) VALUES
('GAL-01', '4.5', 'galerie-lumiere.com', 'Galerie Lumière', '10 Rue des Arts, Paris', '10:00 - 18:00', '0102030405', 'ORG-01'),
('GAL-02', '4.8', 'expo-lyon.fr', 'Espace d''Art Contemporain', '5 Place Bellecour, Lyon', '09:00 - 17:00', '0405060708', 'ORG-02'),
('GAL-03', '4.2', 'le-hangar-art.fr', 'Le Hangar', '45 Quai des Chartrons, Bordeaux', '14:00 - 22:00', '0556575859', 'ORG-03');

-- 7. Insertion de toutes les Expositions
INSERT INTO Exhibition (exhibition_id, title, startDate, endDate, description, curatorName, theme, gallery_id) VALUES
('EXH-01', 'Lumières et Couleurs', '2024-05-01', '2024-05-31', 'Une exploration de la lumière dans l''art.', 'Claire Renard', 'Lumière', 'GAL-01'),
('EXH-02', 'Visions Urbaines', '2024-06-15', '2024-07-15', 'La ville à travers l''objectif.', 'Luc Morel', 'Urbain', 'GAL-02'),
('EXH-03', 'Futurs Possibles', '2024-09-01', '2024-10-31', 'L''impact de la technologie sur la matière.', 'Hugo Vasseur', 'Cyber-Matière', 'GAL-03');

-- 8. Affichage des oeuvres dans les expositions (Liaisons)
INSERT INTO Displays (artwork_id, exhibition_id) VALUES
('AW-01', 'EXH-01'),
('AW-03', 'EXH-01'),
('AW-02', 'EXH-02'),
('AW-04', 'EXH-03'),
('AW-05', 'EXH-03');

-- 9. Insertion de tous les Ateliers (Workshops)
INSERT INTO Workshop (workshop_id, title, dateTime, durationMinutes, maxParticipants, price, location, level, artist_id) VALUES
('WS-01', 'Initiation à l''aquarelle', '2024-05-10 14:00:00', 120, 10, 45.00, 'Atelier 3, Paris', 'Débutant', 'ART-01'),
('WS-02', 'Photographie de nuit', '2024-06-20 21:00:00', 180, 8, 60.00, 'Vieux Lyon', 'Intermédiaire', 'ART-02'),
('WS-03', 'Création sur Blender', '2024-07-10 10:00:00', 240, 15, 80.00, 'Campus Numérique, Bordeaux', 'Intermédiaire', 'ART-03'),
('WS-04', 'Soudure et création', '2024-08-05 09:00:00', 360, 5, 150.00, 'Atelier Métallurgie, Marseille', 'Avancé', 'ART-04');

-- 10. Insertion de tous les Membres
INSERT INTO CommunityMember (member_id, name, email, birthYear, phone, city, membershipType) VALUES
('MEM-01', 'Alice Petit', 'alice.p@email.com', 1995, '0699887766', 'Paris', 'Premium'),
('MEM-02', 'Thomas Blanc', 'thomas.b@email.com', 1988, '0655443322', 'Lyon', 'Standard'),
('MEM-03', 'Camille Simon', 'camille.s@email.com', 2001, '0611111111', 'Bordeaux', 'Premium'),
('MEM-04', 'David Rousseau', 'david.r@email.com', 1982, '0622222222', 'Paris', 'Standard'),
('MEM-05', 'Emma Blanc', 'emma.b@email.com', 1999, '0633333333', 'Marseille', 'Standard');

-- 11. Insertion de toutes les Réservations (Liaisons)
INSERT INTO Booking (workshop_id, member_id, bookingDate, paymentStatus) VALUES
('WS-01', 'MEM-01', '2024-04-20', 'Payé'),
('WS-02', 'MEM-01', '2024-05-05', 'En attente'),
('WS-02', 'MEM-02', '2024-05-10', 'Payé'),
('WS-03', 'MEM-03', '2024-06-01', 'Payé'),
('WS-04', 'MEM-05', '2024-07-15', 'En attente'),
('WS-01', 'MEM-04', '2024-04-25', 'Annulé'),
('WS-01', 'MEM-03', '2024-04-26', 'Payé');

-- 12. Insertion de tous les Avis (Reviews)
INSERT INTO Review (artwork_id, member_id, rating, comment, reviewDate) VALUES
('AW-01', 'MEM-01', 5, 'Magnifique ! Les couleurs sont incroyables.', '2024-05-15'),
('AW-02', 'MEM-02', 4, 'Très belle composition urbaine, j''adore.', '2024-06-25'),
('AW-04', 'MEM-03', 5, 'Une oeuvre numérique fascinante, très immersive.', '2024-09-05'),
('AW-05', 'MEM-05', 4, 'Impressionnant, mais un peu sombre.', '2024-09-10'),
('AW-06', 'MEM-04', 5, 'Acheté pour mon salon, c''est une merveille absolue !', '2024-01-15');


-- views

-- View 1: Hide private information (email, phone) for public display on the app.
CREATE VIEW vw_PublicArtistInfo AS
SELECT artist_id, name, bio, city, website, socialMedia
FROM Artist
WHERE isActive = TRUE;

-- View 2: Easily display artworks for sale with the artist's name without writing complex joins every time.
CREATE VIEW vw_AvailableArtworks AS
SELECT a.artwork_id, a.title, a.medium, a.price, ar.name AS artist_name
FROM Artwork a
JOIN Artist ar ON a.artist_id = ar.artist_id
WHERE a.status = 'Available';

-- View 3: Group information about the exhibition, the gallery, and the organizer in a single virtual table.
CREATE VIEW vw_ExhibitionDetails AS
SELECT e.title AS exhibition_title, e.startDate, e.endDate, e.theme, g.name AS gallery_name, o.name AS organizer_name
FROM Exhibition e
JOIN Gallery g ON e.gallery_id = g.gallery_id
JOIN Organizer o ON g.organizer_id = o.organizer_id;

-- View 4: Provide a clear view of all workshops a member has booked, including dates and payment status.
CREATE VIEW vw_MemberBookingHistory AS
SELECT c.name AS member_name, c.email, w.title AS workshop_title, w.dateTime, b.paymentStatus
FROM CommunityMember c
JOIN Booking b ON c.member_id = b.member_id
JOIN Workshop w ON b.workshop_id = w.workshop_id;

-- View 5: Filter and display only artworks that have received an average rating of 4 or higher.
CREATE VIEW vw_TopRatedArtworks AS
SELECT a.title, a.medium, ar.name AS artist_name, AVG(r.rating) AS average_rating
FROM Artwork a
JOIN Review r ON a.artwork_id = r.artwork_id
JOIN Artist ar ON a.artist_id = ar.artist_id
GROUP BY a.artwork_id, a.title, a.medium, ar.name
HAVING AVG(r.rating) >= 4;


-- Indexes

-- Index 1:
CREATE INDEX idx_artist_name ON Artist(name);
-- This index was created because searching for an artist by name will be one of the most frequent queries on the platform. The idx_artist_name index will help speed-up these searches instead of scanning the full Artist table

-- Index 2:
CREATE INDEX idx_artwork_status ON Artwork(status);
-- The app will need to filter artworks a lot to display only those that are Available in the public catalog.

-- Index 3:
CREATE INDEX idx_workshop_date ON Workshop(dateTime);
-- Indexing the column workshop_date drastically reduces the time required for sorting and filtering to show upcoming events

-- Index 4:
CREATE INDEX idx_member_email ON CommunityMember(email);
-- Searching for a user profile via their email address will occur at every login attempt, so indexing the email column will increase login speed.



-- Triggers:

-- Trigger 1: Prevent the creation of an exhibition that ends before it starts
DELIMITER //
CREATE TRIGGER trg_CheckExhibitionDates
BEFORE INSERT ON Exhibition
FOR EACH ROW
BEGIN
    IF NEW.endDate < NEW.startDate THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: End date cannot be earlier than start date.';
    END IF;
END //
DELIMITER ;


-- Trigger 2: Block a booking if the workshop is already full
DELIMITER //
CREATE TRIGGER trg_CheckWorkshopCapacity
BEFORE INSERT ON Booking
FOR EACH ROW
BEGIN
    DECLARE current_bookings INT;
    DECLARE max_cap INT;

    SELECT COUNT(*) INTO current_bookings FROM Booking WHERE workshop_id = NEW.workshop_id;
    SELECT maxParticipants INTO max_cap FROM Workshop WHERE workshop_id = NEW.workshop_id;

    IF current_bookings >= max_cap THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: This workshop has reached its maximum capacity.';
    END IF;
END //
DELIMITER ;


-- Trigger 3: Prevent the insertion of an artwork with a negative price.
DELIMITER //
CREATE TRIGGER trg_CheckArtworkPrice
BEFORE INSERT ON Artwork
FOR EACH ROW
BEGIN
    IF NEW.price < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Artwork price cannot be negative.';
    END IF;
END //
DELIMITER ;


-- Trigger 4: Ensure that a review rating is strictly between 1 and 5.
DELIMITER //
CREATE TRIGGER trg_CheckRatingLimits
BEFORE INSERT ON Review
FOR EACH ROW
BEGIN
    IF NEW.rating < 1 OR NEW.rating > 5 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rating must be between 1 and 5.';
    END IF;
END //
DELIMITER ;


-- Function 1: Get participant count for an event
DELIMITER //

CREATE FUNCTION fn_GetParticipantCount(w_id VARCHAR(50))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE p_count INT;
    SELECT COUNT(*) INTO p_count
    FROM Booking
    WHERE workshop_id = w_id AND paymentStatus != 'Annulé';

    RETURN p_count;
END //

DELIMITER ;


-- Procedure 1: Update an artwork's status
DELIMITER //
CREATE PROCEDURE sp_UpdateArtworkStatus(IN p_artwork_id VARCHAR(50), IN p_new_status VARCHAR(50))
BEGIN
    UPDATE Artwork
    SET status = p_new_status
    WHERE artwork_id = p_artwork_id;
END //
DELIMITER ;


-- Procedure 2: Get full artist catalog
DELIMITER //
CREATE PROCEDURE sp_GetArtistCatalog(IN p_artist_id VARCHAR(50))
BEGIN
    SELECT title, type, medium, price, status
    FROM Artwork
    WHERE artist_id = p_artist_id;
END //
DELIMITER ;


DELIMITER //

-- Procedure: book two workshops for a Premium member.
-- Manually roll back the transaction if the business conditions are not met.
CREATE PROCEDURE sp_Book_Workshop_Bundle(
    IN p_member_id VARCHAR(50),
    IN p_workshop1_id VARCHAR(50),
    IN p_workshop2_id VARCHAR(50)
)
BEGIN
    -- Variable to store the member's type
    DECLARE v_member_type VARCHAR(50);

    SELECT membershipType INTO v_member_type
    FROM CommunityMember
    WHERE member_id = p_member_id;

    START TRANSACTION;

    IF v_member_type != 'Premium' THEN
        -- User not premium --> We cancel the transaction
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: only Premium members can book multiple workshops at once.';

    ELSE
        -- Book the first workshop
        INSERT INTO Booking (workshop_id, member_id, bookingDate, paymentStatus)
        VALUES (p_workshop1_id, p_member_id, CURDATE(), 'Paid');

        -- Book the second workshop
        INSERT INTO Booking (workshop_id, member_id, bookingDate, paymentStatus)
        VALUES (p_workshop2_id, p_member_id, CURDATE(), 'Paid');

        COMMIT;
    END IF;

END //

DELIMITER ;

CALL sp_Book_Workshop_Bundle('MEM-02', 'WS-03', 'WS-04');

CALL sp_Book_Workshop_Bundle('MEM-03', 'WS-02', 'WS-04');

SELECT c.name, b.workshop_id, b.bookingDate, b.paymentStatus
FROM Booking b
JOIN CommunityMember c ON b.member_id = c.member_id
WHERE c.member_id = 'MEM-03';

