CREATE TABLE `fac_clientes` (
  `clie_id` varchar(255) NOT NULL,
  `clie_apellido` varchar(255) DEFAULT NULL,
  `clie_direccion` varchar(255) DEFAULT NULL,
  `clie_nombre` varchar(255) DEFAULT NULL,
  `clie_num_doc` varchar(255) DEFAULT NULL,
  `clie_telefono` varchar(255) DEFAULT NULL,
  `clie_tip_doc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clie_id`)
) ENGINE=InnoDB;


CREATE TABLE `fac_facturas` (
  `fac_id` varchar(255) NOT NULL,
  `fac_fec_emis` date DEFAULT NULL,
  `fac_fec_venc` date DEFAULT NULL,
  `fac_impuesto` decimal(19,2) DEFAULT NULL,
  `fac_estatus` varchar(255) DEFAULT NULL,
  `fac_subtotal` decimal(19,2) DEFAULT NULL,
  `fac_total` decimal(19,2) DEFAULT NULL,
  `clie_id` varchar(255) NOT NULL,
  PRIMARY KEY (`fac_id`),
  KEY `FKii1msme2hg1v4hnkxmsbwa7eo` (`clie_id`),
  CONSTRAINT `FKii1msme2hg1v4hnkxmsbwa7eo` FOREIGN KEY (`clie_id`) REFERENCES `fac_clientes` (`clie_id`)
) ENGINE=InnoDB;



CREATE TABLE `fac_clientes_facturas` (
  `cliente_clie_id` varchar(255) NOT NULL,
  `facturas_fac_id` varchar(255) NOT NULL,
  UNIQUE KEY `UK_ic7l0o1h22b98tgu8jpa69cw1` (`facturas_fac_id`),
  KEY `FKhjdol863booensqkxbyp6aogt` (`cliente_clie_id`),
  CONSTRAINT `FKhjdol863booensqkxbyp6aogt` FOREIGN KEY (`cliente_clie_id`) REFERENCES `fac_clientes` (`clie_id`),
  CONSTRAINT `FKkxvrv0mh20pbt4jvqov3l780t` FOREIGN KEY (`facturas_fac_id`) REFERENCES `fac_facturas` (`fac_id`)
) ENGINE=InnoDB;


CREATE TABLE `productos` (
  `prod_id` varchar(255) NOT NULL,
  `prod_descripcion` varchar(255) DEFAULT NULL,
  `prod_nombre` varchar(255) DEFAULT NULL,
  `prod_precio` decimal(19,2) DEFAULT NULL,
  `prod_stock` int DEFAULT NULL,
  PRIMARY KEY (`prod_id`)
) ENGINE=InnoDB;

CREATE TABLE `fac_prod_factura` (
  `pfac_id` varchar(255) NOT NULL,
  `pfac_cantidad` int DEFAULT NULL,
  `factura_fac_id` varchar(255) DEFAULT NULL,
  `producto_prod_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pfac_id`),
  KEY `FKoosx28acnee2hw9mh2dy9qhtl` (`factura_fac_id`),
  KEY `FKjxfu33cr37llir4mhlnx3ml4j` (`producto_prod_id`),
  CONSTRAINT `FKjxfu33cr37llir4mhlnx3ml4j` FOREIGN KEY (`producto_prod_id`) REFERENCES `productos` (`prod_id`),
  CONSTRAINT `FKoosx28acnee2hw9mh2dy9qhtl` FOREIGN KEY (`factura_fac_id`) REFERENCES `fac_facturas` (`fac_id`)
) ENGINE=InnoDB;


CREATE TABLE `fac_facturas_productos` (
  `factura_fac_id` varchar(255) NOT NULL,
  `productos_pfac_id` varchar(255) NOT NULL,
  UNIQUE KEY `UK_pc6lfadfkwb0d8g2jfdia4ybp` (`productos_pfac_id`),
  KEY `FKa57494snlcg8ndq0ru41hvwrl` (`factura_fac_id`),
  CONSTRAINT `FK428yjqbwif7gdd2ctybnduix0` FOREIGN KEY (`productos_pfac_id`) REFERENCES `fac_prod_factura` (`pfac_id`),
  CONSTRAINT `FKa57494snlcg8ndq0ru41hvwrl` FOREIGN KEY (`factura_fac_id`) REFERENCES `fac_facturas` (`fac_id`)
) ENGINE=InnoDB;


