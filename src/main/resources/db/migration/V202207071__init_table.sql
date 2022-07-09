DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS photo;
DROP TABLE IF EXISTS point;
DROP TABLE IF EXISTS point_history;
DROP TABLE IF EXISTS point_total;
DROP TABLE IF EXISTS review;

CREATE TABLE users (
    users_id VARCHAR(63) PRIMARY KEY,
    nickname VARCHAR(63) NOT NULL
);

CREATE TABLE place (
    place_id VARCHAR(63) PRIMARY KEY,
    place_name VARCHAR(63)
);

CREATE TABLE review (
    review_id VARCHAR(63) PRIMARY KEY,
    created_at TIMESTAMP,
    content VARCHAR(255),
    place_id VARCHAR(63),
    FOREIGN KEY(place_id) REFERENCES place(place_id),
    users_id VARCHAR(63),
    FOREIGN KEY(users_id) REFERENCES users(users_id),
    review_type VARCHAR(63)
);

CREATE TABLE photo (
    photo_id VARCHAR(63) PRIMARY KEY,
    url VARCHAR(255),
    review_id VARCHAR(63),
    FOREIGN KEY(review_id) REFERENCES review(review_id)
);

CREATE TABLE point (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP,
    event_type VARCHAR(63),
    point_score BIGINT,
    target_id VARCHAR(63),
    users_id VARCHAR(63),
    FOREIGN KEY(users_id) REFERENCES users(users_id)
);

CREATE TABLE point_history (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP,
    event_type VARCHAR(63),
    action_type VARCHAR(63),
    point BIGINT,
    target_id VARCHAR(63),
    users_id VARCHAR(63),
    FOREIGN KEY(users_id) REFERENCES users(users_id)
);

CREATE TABLE point_total (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    total BIGINT,
    users_id VARCHAR(63),
    FOREIGN KEY(users_id) REFERENCES users(users_id)
);

CREATE INDEX idx_review_users_place ON review (users_id, place_id);
CREATE INDEX idx_review_users_review_id ON review (users_id, review_id);

CREATE INDEX idx_point_users ON point (users_id);
CREATE INDEX idx_point_users_target_id ON point (users_id, target_id);

CREATE INDEX idx_point_history_users ON point_history (users_id);

CREATE INDEX idx_point_total_users ON point_total (users_id);
