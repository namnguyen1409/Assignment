import os

entityPath = r"C:\Users\namnguyen\OneDrive\Documents\NetBeansProjects\Assignment\src\main\java\com\assignment\models\entities"
enumPath = r"C:\Users\namnguyen\OneDrive\Documents\NetBeansProjects\Assignment\src\main\java\com\assignment\models\entities\EntityList.java"

enum = """
package com.assignment.models.entities;

public enum EntityList {
    ***

    private final String value;

    EntityList(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
"""

def generate_enum(entityPath, enum):
    entities = []
    for root, dirs, files in os.walk(entityPath):
        for file in files:
            if file.endswith(".java"):
                entities.append(file.split(".")[0])
    entities.sort()
    entityList = ""
    for entity in entities:
        entityList += entity[0].upper()
        for i in range(1, len(entity)):
            if entity[i].isupper():
                entityList += "_"
            entityList += entity[i].upper()
        entityList += "(\"" + entity + "\"),\n    "
    enum = enum.replace("***", entityList)
    with open(enumPath, "w", encoding="utf-8") as f:
        f.write(enum)

generate_enum(entityPath, enum)