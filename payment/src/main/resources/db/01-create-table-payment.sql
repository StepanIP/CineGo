CREATE TABLE payments
(
    id BIGSERIAL PRIMARY KEY,
    amount            DOUBLE PRECISION,
    currency          VARCHAR(10),
    description       VARCHAR(255),
    status            VARCHAR(50),
    paypal_payment_id VARCHAR(255),
    ticket_id         BIGINT
);
