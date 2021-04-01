CREATE TABLE IF NOT EXISTS bucket (
    id BIGSERIAL PRIMARY KEY,
    bucket_id UUID UNIQUE NOT NULL,
    position DECIMAL UNIQUE NOT NULL CHECK (position > 0),
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
