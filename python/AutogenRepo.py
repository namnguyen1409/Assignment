import os
code_template = """package com.assignment.models.repositories.shop.**;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.**.***;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ***Repo extends Repositories<***, Long> {

    @Override
    protected Class<***> getEntityClass() {
        return ***.class;
    }

}

"""

source_path = r"C:\Users\namnguyen\OneDrive\Documents\NetBeansProjects\Assignment\src\main\java\com\assignment\models\entities\ewallet"
target_path = r"C:\Users\namnguyen\OneDrive\Documents\NetBeansProjects\Assignment\src\main\java\com\assignment\models\repositories\ewallet"

def generate_code(source_path, target_path, code_template):
    package = source_path.split("\\")[-1]
    for file in os.listdir(source_path):
        if file.endswith(".java"):
            entity_name = file.split(".")[0]
            code = code_template.replace("***", entity_name)
            code = code.replace("**", package)
            with open(target_path + "\\" + entity_name + "Repo.java", "w", encoding="utf-8") as f:
                f.write(code)

generate_code(source_path, target_path, code_template)