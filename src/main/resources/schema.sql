CREATE TABLE IF NOT EXISTS todos (
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(200) NOT NULL,
    description      TEXT,
    completed        BOOLEAN NOT NULL DEFAULT FALSE,
    start_datetime   TIMESTAMPTZ NOT NULL,
    end_datetime     TIMESTAMPTZ,
    CONSTRAINT chk_dates CHECK (end_datetime IS NULL OR end_datetime >= start_datetime)
    );
