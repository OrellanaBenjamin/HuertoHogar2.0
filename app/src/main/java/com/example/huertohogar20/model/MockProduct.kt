package com.example.huertohogar20.model

val categorias = listOf(
    "Frutas Frescas",
    "Verduras Orgánicas",
    "Productos Orgánicos",
    "Productos Lácteos"
)

val mockProducts = listOf(
    // Frutas Frescas
    Product(
        codigo = "FR001",
        nombre = "Manzanas Fuji",
        descripcion = "Manzanas crujientes y dulces, cultivadas en el Valle del Maule. Textura firme y sabor equilibrado.",
        precio = 1200.0,
        stock = 150,
        imagen = "manzanas_fuji.png",
        categoria = "Frutas Frescas"
    ),
    Product(
        codigo = "FR002",
        nombre = "Naranjas Valencia",
        descripcion = "Jugosas y ricas en vitamina C. Ideales para zumos frescos y meriendas saludables.",
        precio = 1000.0,
        stock = 200,
        imagen = "naranja_valencia.png",
        categoria = "Frutas Frescas"
    ),
    Product(
        codigo = "FR003",
        nombre = "Plátanos Cavendish",
        descripcion = "Maduros, dulces y ricos en potasio. Perfectos para snacks y desayunos.",
        precio = 800.0,
        stock = 250,
        imagen = "platano_cavendish.png",
        categoria = "Frutas Frescas"
    ),

    // Verduras Orgánicas
    Product(
        codigo = "VR001",
        nombre = "Zanahorias Orgánicas",
        descripcion = "Crujientes y sin pesticidas. Excelente fuente de vitamina A y fibra.",
        precio = 900.0,
        stock = 100,
        imagen = "zanahoria_organica.png",
        categoria = "Verduras Orgánicas"
    ),
    Product(
        codigo = "VR002",
        nombre = "Espinacas Frescas",
        descripcion = "Nutritivas y listas para ensaladas o batidos. Cultivo orgánico garantizado.",
        precio = 700.0,
        stock = 80,
        imagen = "espinaca.png",
        categoria = "Verduras Orgánicas"
    ),
    Product(
        codigo = "VR003",
        nombre = "Pimientos Tricolores",
        descripcion = "Rojo, amarillo y verde. Añade color y antioxidantes a tus recetas.",
        precio = 1500.0,
        stock = 120,
        imagen = "pimientos_tricolores.png",
        categoria = "Verduras Orgánicas"
    ),

    // Productos Orgánicos
    Product(
        codigo = "PO001",
        nombre = "Miel Orgánica",
        descripcion = "Miel pura producida localmente, rica en antioxidantes.",
        precio = 5000.0,
        stock = 50,
        imagen = "miel_organica.png",
        categoria = "Productos Orgánicos"
    ),
    Product(
        codigo = "PO002",
        nombre = "Quinua Orgánica",
        descripcion = "Semilla nutritiva y versátil para todo tipo de platillos.",
        precio = 3500.0,
        stock = 60,
        imagen = "quinua_organica.png",
        categoria = "Productos Orgánicos"
    ),
    Product(
        codigo = "PO003",
        nombre = "Aceite de Oliva Extra Virgen",
        descripcion = "Premiado, prensado en frío. Ideal para ensaladas y cocina gourmet.",
        precio = 4200.0,
        stock = 45,
        imagen = "aceite_oliva.png",
        categoria = "Productos Orgánicos"
    ),

    // Productos Lácteos
    Product(
        codigo = "PL001",
        nombre = "Leche Entera",
        descripcion = "Fresca y producida localmente. Rica en calcio y sabor auténtico.",
        precio = 1200.0,
        stock = 70,
        imagen = "leche_entera.png",
        categoria = "Productos Lácteos"
    ),
    Product(
        codigo = "PL002",
        nombre = "Yogur Natural",
        descripcion = "Sin aditivos, lejos de lo industrializado. Perfecto para desayunos.",
        precio = 1800.0,
        stock = 80,
        imagen = "yogur_natural.png",
        categoria = "Productos Lácteos"
    ),
    Product(
        codigo = "PL003",
        nombre = "Queso Fresco",
        descripcion = "Hecho artesanalmente, ideal para ensaladas y comidas ligeras.",
        precio = 2500.0,
        stock = 55,
        imagen = "queso_fresco.png",
        categoria = "Productos Lácteos"
    ),
    Product(
        codigo = "PL004",
        nombre = "Mantequilla Tradicional",
        descripcion = "Elaborada con leche de vaca local y procesos artesanales.",
        precio = 1700.0,
        stock = 40,
        imagen = "mantequilla.png",
        categoria = "Productos Lácteos"
    ),

    // 3 extras para variedad
    Product(
        codigo = "PO004",
        nombre = "Semillas de Zapallo",
        descripcion = "Semillas tostadas ideales para snack saludable.",
        precio = 1200.0,
        stock = 100,
        imagen = "zapallo.png",
        categoria = "Productos Orgánicos"
    ),
    Product(
        codigo = "VR004",
        nombre = "Brócoli Orgánico",
        descripcion = "Brócoli fresco sin pesticidas, fuente de vitaminas y fibra.",
        precio = 1300.0,
        stock = 90,
        imagen = "brocoli.png",
        categoria = "Verduras Orgánicas"
    ),
    Product(
        codigo = "PO005",
        nombre = "Granola Natural",
        descripcion = "Granola artesanal con frutos secos y semillas orgánicas.",
        precio = 2500.0,
        stock = 65,
        imagen = "granola.png",
        categoria = "Productos Orgánicos"
    ),
    Product(
        codigo = "CAJA001",
        nombre = "Caja Mixta de Verduras",
        descripcion = "Caja completa con: 1kg de zanahorias orgánicas, 1kg de tomates cherry, 500g de lechugas variadas, 500g de espinacas frescas y 1kg de papas orgánicas. Ideal para 4-5 personas por semana.",
        precio = 15990.0,
        stock = 25,
        imagen = "no_disponible.png",
        categoria = "Productos Orgánicos"
    ),
    Product(
        codigo = "CAJA002",
        nombre = "Caja de Frutas de Temporada",
        descripcion = "Incluye: 1kg de manzanas orgánicas, 1kg de peras, 500g de uvas, 6 kiwis, 4 naranjas y 2 paltas. Frutas frescas de temporada seleccionadas.",
        precio = 18900.0,
        stock = 20,
        imagen = "no_disponible.png",
        categoria = "Frutas Frescas"
    ),
    Product(
        codigo = "CAJA003",
        nombre = "Caja Familiar Completa",
        descripcion = "Pack familiar con: 2kg de zanahorias, 1.5kg de tomates, 1kg de pimentones variados, 1kg de cebollas, 500g de ajos, 1kg de papas y 500g de porotos verdes. Suficiente para 7 días.",
        precio = 24990.0,
        stock = 15,
        imagen = "no_disponible.png",
        categoria = "Verduras Orgánicas"
    ),
    Product(
        codigo = "CAJA004",
        nombre = "Caja Gourmet de Aceites",
        descripcion = "Selección premium: 2 botellas de Aceite de Oliva Extra Virgen 500ml, 1 botella de aceite de coco orgánico 250ml, 1 botella de aceite de aguacate 250ml.",
        precio = 35990.0,
        stock = 12,
        imagen = "no_disponible.png",
        categoria = "Productos Orgánicos"
    ),
    Product(
        codigo = "CAJA005",
        nombre = "Caja Ensaladas Frescas",
        descripcion = "Perfecta para ensaladas: 3 lechugas variadas, 500g de rúcula, 500g de espinaca baby, 300g de mix de hojas verdes, 250g de berros y germinados. Rinde 10-12 ensaladas.",
        precio = 12990.0,
        stock = 30,
        imagen = "no_disponible.png",
        categoria = "Verduras Orgánicas"
    )
)