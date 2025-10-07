-- Datos iniciales para la tabla de productos
-- Se cargan automáticamente al iniciar la aplicación

INSERT INTO productos (nombre, descripcion, precio, stock, fecha_creacion, fecha_actualizacion, activo)
VALUES
  ('Laptop Dell XPS 13', 'Laptop ultraligera con procesador Intel i7, 16GB RAM, 512GB SSD', 1299.99, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Mouse Logitech MX Master 3', 'Mouse inalámbrico ergonómico con sensor de alta precisión', 99.99, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Teclado Mecánico Corsair K95', 'Teclado mecánico RGB con switches Cherry MX', 179.99, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Monitor LG UltraWide 34"', 'Monitor curvo 34 pulgadas, resolución 3440x1440, 144Hz', 599.99, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Webcam Logitech C920', 'Cámara web HD 1080p con micrófono integrado', 79.99, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Audífonos Sony WH-1000XM4', 'Audífonos inalámbricos con cancelación de ruido', 349.99, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('SSD Samsung 970 EVO 1TB', 'Disco de estado sólido NVMe M.2, velocidad de lectura 3500MB/s', 149.99, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Impresora HP LaserJet Pro', 'Impresora láser monocromática, velocidad 40ppm', 299.99, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Router ASUS RT-AX88U', 'Router WiFi 6 de doble banda con 8 puertos Gigabit', 329.99, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
  ('Hub USB-C Anker', 'Hub 7 en 1 con HDMI, USB 3.0, lector SD y carga PD', 49.99, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
