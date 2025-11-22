CREATE TYPE role AS ENUM ('STUDENT', 'TEACHER', 'ADMIN');
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE', 'OTHER');
CREATE TYPE access_type AS ENUM ('PUBLIC', 'PRIVATE');
CREATE TYPE thread_type AS ENUM ('DISCUSSION', 'QUESTION', 'ANNOUNCEMENT');
CREATE TYPE question_type AS ENUM ('MULTIPLE_CHOICE', 'SHORT_ANSWER');
CREATE TYPE submission_status AS ENUM ('PENDING', 'SUBMITTED', 'GRADED');

CREATE TABLE user_humg (
                           id               SERIAL PRIMARY KEY,
                           first_name       VARCHAR(25)  NOT NULL,
                           last_name        VARCHAR(25)  NOT NULL,
                           email            VARCHAR(255) NOT NULL UNIQUE,
                           password_hash    VARCHAR(255) NOT NULL,
                           gender           gender  NOT NULL,
                           birthday         DATE         NOT NULL,
                           role             role    NOT NULL DEFAULT 'STUDENT',
                           phone            VARCHAR(20),
                           university_name  VARCHAR(50),
                           faculty_name     VARCHAR(50),
                           avatar_url       TEXT,
                           is_active        BOOLEAN      NOT NULL DEFAULT TRUE,
                           created          TIMESTAMP    NOT NULL DEFAULT NOW(),
                           updated          TIMESTAMP    NOT NULL DEFAULT NOW()
);
CREATE TABLE document (
                          id             BIGSERIAL PRIMARY KEY,
                          owner_id       BIGINT NOT NULL REFERENCES user_humg(id),
                          title          VARCHAR(55) NOT NULL,
                          description    VARCHAR(155),
                          file_url       TEXT NOT NULL,
                          file_type      VARCHAR(20) NOT NULL,
                          file_size      BIGINT NOT NULL,
                          subject_name   VARCHAR(30),
                          access_type    access_type NOT NULL DEFAULT 'PUBLIC',
                          is_active      BOOLEAN NOT NULL DEFAULT TRUE,
                          created        TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE forum_thread (
                              id            BIGSERIAL PRIMARY KEY,
                              owner_id      BIGINT NOT NULL REFERENCES user_humg(id),
                              title         VARCHAR(55) NOT NULL,
                              content       TEXT NOT NULL,
                              type          thread_type NOT NULL DEFAULT 'DISCUSSION',
                              is_pinned     BOOLEAN NOT NULL DEFAULT FALSE,
                              is_active     BOOLEAN NOT NULL DEFAULT TRUE,
                              created       TIMESTAMP NOT NULL DEFAULT NOW(),
                              updated       TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE forum_post (
                            id          BIGSERIAL PRIMARY KEY,
                            owner_id    BIGINT NOT NULL REFERENCES user_humg(id),
                            thread_id   BIGINT NOT NULL REFERENCES forum_thread(id) ON DELETE CASCADE,
                            content     TEXT NOT NULL,
                            is_active   BOOLEAN NOT NULL DEFAULT TRUE,
                            created     TIMESTAMP NOT NULL DEFAULT NOW(),
                            updated     TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE assignment (
                            id              BIGSERIAL PRIMARY KEY,
                            title           VARCHAR(55) NOT NULL,
                            owner_id        BIGINT NOT NULL REFERENCES user_humg(id),
                            description     VARCHAR(155),
                            subject_name    VARCHAR(30),
                            start_time      TIMESTAMP,
                            end_time        TIMESTAMP,
                            is_active       BOOLEAN NOT NULL DEFAULT TRUE,
                            created         TIMESTAMP NOT NULL DEFAULT NOW(),
                            updated         TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE question (
                          id               BIGSERIAL PRIMARY KEY,
                          assignment_id    BIGINT NOT NULL REFERENCES assignment(id) ON DELETE CASCADE,
                          type             question_type NOT NULL,
                          content          TEXT NOT NULL,
                          correct_answer   TEXT,
                          score            INT NOT NULL DEFAULT 1,
                          is_active        BOOLEAN NOT NULL DEFAULT TRUE,
                          created          TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated          TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE question_option (
                                 id            BIGSERIAL PRIMARY KEY,
                                 question_id   BIGINT NOT NULL REFERENCES question(id) ON DELETE CASCADE,
                                 option_key    VARCHAR(3) NOT NULL,
                                 content       TEXT NOT NULL
);

CREATE TABLE assignment_submission (
                                       id            BIGSERIAL PRIMARY KEY,
                                       assignment_id BIGINT NOT NULL REFERENCES assignment(id) ON DELETE CASCADE,
                                       student_id    BIGINT NOT NULL REFERENCES user_humg(id),
                                       status        submission_status NOT NULL DEFAULT 'SUBMITTED',
                                       score_total   INT,
                                       submitted  TIMESTAMP NOT NULL DEFAULT NOW(),
                                       graded     TIMESTAMP
);
