CREATE TABLE Brand (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE Category (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE Product (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL,
  grade char,
  brandId int,
  categoryId int
);

CREATE TABLE ProductIngrediant (
  productId int,
  ingrediantId int
);

CREATE TABLE ProductNutrient (
  productId int,
  nurtientId int,
  value float DEFAULT 0
);

CREATE TABLE Nutrient (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE Ingrediant (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE ProductAllergen (
  productId int,
  allergenId int
);

CREATE TABLE Allergen (
  id serial PRIMARY KEY,
  language varchar UNIQUE NOT NULL,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE ProductAdditive (
  productId int,
  additiveId int
);

CREATE TABLE Additive (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL,
  code varchar,
  alt varchar
);

ALTER TABLE Product ADD FOREIGN KEY (brandId) REFERENCES Brand (id);

ALTER TABLE Product ADD FOREIGN KEY (categoryId) REFERENCES Category (id);

CREATE TABLE Product_ProductIngrediant (
  Product_id serial,
  ProductIngrediant_productId int,
  PRIMARY KEY (Product_id, ProductIngrediant_productId)
);

ALTER TABLE Product_ProductIngrediant ADD FOREIGN KEY (Product_id) REFERENCES Product (id);

ALTER TABLE Product_ProductIngrediant ADD FOREIGN KEY (ProductIngrediant_productId) REFERENCES ProductIngrediant (productId);


CREATE TABLE Ingrediant_ProductIngrediant (
  Ingrediant_id serial,
  ProductIngrediant_ingrediantId int,
  PRIMARY KEY (Ingrediant_id, ProductIngrediant_ingrediantId)
);

ALTER TABLE Ingrediant_ProductIngrediant ADD FOREIGN KEY (Ingrediant_id) REFERENCES Ingrediant (id);

ALTER TABLE Ingrediant_ProductIngrediant ADD FOREIGN KEY (ProductIngrediant_ingrediantId) REFERENCES ProductIngrediant (ingrediantId);


CREATE TABLE Product_ProductNutrient (
  Product_id serial,
  ProductNutrient_productId int,
  PRIMARY KEY (Product_id, ProductNutrient_productId)
);

ALTER TABLE Product_ProductNutrient ADD FOREIGN KEY (Product_id) REFERENCES Product (id);

ALTER TABLE Product_ProductNutrient ADD FOREIGN KEY (ProductNutrient_productId) REFERENCES ProductNutrient (productId);


CREATE TABLE Nutrient_ProductNutrient (
  Nutrient_id serial,
  ProductNutrient_nurtientId int,
  PRIMARY KEY (Nutrient_id, ProductNutrient_nurtientId)
);

ALTER TABLE Nutrient_ProductNutrient ADD FOREIGN KEY (Nutrient_id) REFERENCES Nutrient (id);

ALTER TABLE Nutrient_ProductNutrient ADD FOREIGN KEY (ProductNutrient_nurtientId) REFERENCES ProductNutrient (nurtientId);


CREATE TABLE Product_ProductAllergen (
  Product_id serial,
  ProductAllergen_productId int,
  PRIMARY KEY (Product_id, ProductAllergen_productId)
);

ALTER TABLE Product_ProductAllergen ADD FOREIGN KEY (Product_id) REFERENCES Product (id);

ALTER TABLE Product_ProductAllergen ADD FOREIGN KEY (ProductAllergen_productId) REFERENCES ProductAllergen (productId);


CREATE TABLE Allergen_ProductAllergen (
  Allergen_id serial,
  ProductAllergen_allergenId int,
  PRIMARY KEY (Allergen_id, ProductAllergen_allergenId)
);

ALTER TABLE Allergen_ProductAllergen ADD FOREIGN KEY (Allergen_id) REFERENCES Allergen (id);

ALTER TABLE Allergen_ProductAllergen ADD FOREIGN KEY (ProductAllergen_allergenId) REFERENCES ProductAllergen (allergenId);


CREATE TABLE Product_ProductAdditive (
  Product_id serial,
  ProductAdditive_productId int,
  PRIMARY KEY (Product_id, ProductAdditive_productId)
);

ALTER TABLE Product_ProductAdditive ADD FOREIGN KEY (Product_id) REFERENCES Product (id);

ALTER TABLE Product_ProductAdditive ADD FOREIGN KEY (ProductAdditive_productId) REFERENCES ProductAdditive (productId);


CREATE TABLE Additive_ProductAdditive (
  Additive_id serial,
  ProductAdditive_additiveId int,
  PRIMARY KEY (Additive_id, ProductAdditive_additiveId)
);

ALTER TABLE Additive_ProductAdditive ADD FOREIGN KEY (Additive_id) REFERENCES Additive (id);

ALTER TABLE Additive_ProductAdditive ADD FOREIGN KEY (ProductAdditive_additiveId) REFERENCES ProductAdditive (additiveId);
