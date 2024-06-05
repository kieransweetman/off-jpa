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
  grade int,
  brandId int,
  categoryId int
);
CREATE TABLE Allergen (
  id serial PRIMARY KEY,
  language varchar UNIQUE NOT NULL,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE Nutrient (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE Ingrediant (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE Additive (
  id serial PRIMARY KEY,
  name varchar UNIQUE NOT NULL,
  code varchar,
  alt varchar
);


CREATE TABLE ProductAllergen (
  productId int,
  allergenId int,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (allergenId) REFERENCES Allergen (id)
);



CREATE TABLE ProductAdditive (
  productId int,
  additiveId int,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (additiveId) REFERENCES Additive (id)
);


CREATE TABLE ProductIngrediant (
  productId int,
  ingrediantId int,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (ingrediantId) REFERENCES Ingrediant (id)

);

CREATE TABLE ProductNutrient (
  productId int,
  nurtientId int,
  value float DEFAULT 0,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (nurtientId) REFERENCES Nutrient (id)
);

ALTER TABLE Product ADD FOREIGN KEY (brandId) REFERENCES Brand (id);

ALTER TABLE Product ADD FOREIGN KEY (categoryId) REFERENCES Category (id);

