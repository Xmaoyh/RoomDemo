/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.demoroom.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] NAME = new String[]{
            "TMC", "KB", "KD", "LBJ", "Dirk Nowitzki"};
    private static final String[] ADDRESS = new String[]{
            "Houston", "LA", "Golden State", "Los Angeles", "Dallas"};


    public static List<UserEntity> generateUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 0; i < NAME.length; i++) {
            UserEntity product = new UserEntity();
            product.setName(NAME[ i ]);
            product.setAddress(ADDRESS[ i ]);
            userEntities.add(product);
        }
        return userEntities;
    }

}
