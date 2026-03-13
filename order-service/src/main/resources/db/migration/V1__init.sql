CREATE TABLE IF NOT EXISTS `orders` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_number` VARCHAR(255) NOT NULL,
    `sku_code` VARCHAR(255),
    `price` DECIMAL(19, 2),
    `quantity` INT
);