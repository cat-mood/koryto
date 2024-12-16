UPDATE parts
SET price = CASE
                WHEN part_name = 'Oil Filter' THEN 12.99
                WHEN part_name = 'Spark Plug' THEN 15.49
                WHEN part_name = 'Brake Pad Set' THEN 45.99
                WHEN part_name = 'Exhaust Muffler' THEN 120.00
                WHEN part_name = 'Shock Absorber' THEN 75.00
                WHEN part_name = 'Air Filter' THEN 18.50
                WHEN part_name = 'Alternator' THEN 200.00
                WHEN part_name = 'Disc Rotor' THEN 80.00
                WHEN part_name = 'Catalytic Converter' THEN 300.00
                WHEN part_name = 'Coil Spring' THEN 60.00
                ELSE price
    END;
