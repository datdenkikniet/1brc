/*
 *  Copyright 2023 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.morling.onebrc;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

class WeatherStation {
    private String id;
    private double meanTemperature;
    private Random random;

    public WeatherStation(long seed, String id, double meanTemperature) {
        this.id = id;
        this.meanTemperature = meanTemperature;
        this.random = new Random((long) id.hashCode() ^ seed);
    }

    public String id() {
        return this.id;
    }

    public double measurement() {
        double m = this.random.nextGaussian(this.meanTemperature, 10);
        return Math.round(m * 10.0) / 10.0;
    }
}

public class CreateMeasurements {

    private static final Path MEASUREMENT_FILE = Path.of("./measurements.txt");

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        if (args.length < 1) {
            System.out.println("Usage: create_measurements.sh <number of records to create> [seed]");
            System.exit(1);
        }

        int size = 0;
        try {
            size = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid value for <number of records to create>");
            System.out.println("Usage: CreateMeasurements <number of records to create> [seed]");
            System.exit(1);
        }

        // Default seed is 1brc1brc converted to hexadecimal
        long seed = 0x3162726331627263L;
        if (args.length == 2) {
            try {
                seed = Long.parseLong(args[1]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid value for [seed]");
                System.out.println("Usage: CreateMeasurements <number of records to create> [seed]");
                System.exit(1);
            }
        }

        // @formatter:off
        // data from https://en.wikipedia.org/wiki/List_of_cities_by_average_temperature;
        // converted using https://wikitable2csv.ggor.de/
        // brought to form using DuckDB:
        // D copy (
        //     select City, regexp_extract(Year,'(.*)\n.*', 1) as AverageTemp
        //     from (
        //         select City,Year
        //         from read_csv_auto('List_of_cities_by_average_temperature_1.csv', header = true)
        //         union
        //         select City,Year
        //         from read_csv_auto('List_of_cities_by_average_temperature_2.csv', header = true)
        //         union
        //         select City,Year
        //         from read_csv_auto('List_of_cities_by_average_temperature_3.csv', header = true)
        //         union
        //         select City,Year
        //         from read_csv_auto('List_of_cities_by_average_temperature_4.csv', header = true)
        //         union
        //         select City,Year
        //         from read_csv_auto('List_of_cities_by_average_temperature_5.csv', header = true)
        //         )
        // ) TO 'output.csv' (HEADER, DELIMITER ',');
        // @formatter:on
        List<WeatherStation> stations = Arrays.asList(
                new WeatherStation(seed, "Abha", 18.0),
                new WeatherStation(seed, "Abidjan", 26.0),
                new WeatherStation(seed, "Abéché", 29.4),
                new WeatherStation(seed, "Accra", 26.4),
                new WeatherStation(seed, "Addis Ababa", 16.0),
                new WeatherStation(seed, "Adelaide", 17.3),
                new WeatherStation(seed, "Aden", 29.1),
                new WeatherStation(seed, "Ahvaz", 25.4),
                new WeatherStation(seed, "Albuquerque", 14.0),
                new WeatherStation(seed, "Alexandra", 11.0),
                new WeatherStation(seed, "Alexandria", 20.0),
                new WeatherStation(seed, "Algiers", 18.2),
                new WeatherStation(seed, "Alice Springs", 21.0),
                new WeatherStation(seed, "Almaty", 10.0),
                new WeatherStation(seed, "Amsterdam", 10.2),
                new WeatherStation(seed, "Anadyr", -6.9),
                new WeatherStation(seed, "Anchorage", 2.8),
                new WeatherStation(seed, "Andorra la Vella", 9.8),
                new WeatherStation(seed, "Ankara", 12.0),
                new WeatherStation(seed, "Antananarivo", 17.9),
                new WeatherStation(seed, "Antsiranana", 25.2),
                new WeatherStation(seed, "Arkhangelsk", 1.3),
                new WeatherStation(seed, "Ashgabat", 17.1),
                new WeatherStation(seed, "Asmara", 15.6),
                new WeatherStation(seed, "Assab", 30.5),
                new WeatherStation(seed, "Astana", 3.5),
                new WeatherStation(seed, "Athens", 19.2),
                new WeatherStation(seed, "Atlanta", 17.0),
                new WeatherStation(seed, "Auckland", 15.2),
                new WeatherStation(seed, "Austin", 20.7),
                new WeatherStation(seed, "Baghdad", 22.77),
                new WeatherStation(seed, "Baguio", 19.5),
                new WeatherStation(seed, "Baku", 15.1),
                new WeatherStation(seed, "Baltimore", 13.1),
                new WeatherStation(seed, "Bamako", 27.8),
                new WeatherStation(seed, "Bangkok", 28.6),
                new WeatherStation(seed, "Bangui", 26.0),
                new WeatherStation(seed, "Banjul", 26.0),
                new WeatherStation(seed, "Barcelona", 18.2),
                new WeatherStation(seed, "Bata", 25.1),
                new WeatherStation(seed, "Batumi", 14.0),
                new WeatherStation(seed, "Beijing", 12.9),
                new WeatherStation(seed, "Beirut", 20.9),
                new WeatherStation(seed, "Belgrade", 12.5),
                new WeatherStation(seed, "Belize City", 26.7),
                new WeatherStation(seed, "Benghazi", 19.9),
                new WeatherStation(seed, "Bergen", 7.7),
                new WeatherStation(seed, "Berlin", 10.3),
                new WeatherStation(seed, "Bilbao", 14.7),
                new WeatherStation(seed, "Birao", 26.5),
                new WeatherStation(seed, "Bishkek", 11.3),
                new WeatherStation(seed, "Bissau", 27.0),
                new WeatherStation(seed, "Blantyre", 22.2),
                new WeatherStation(seed, "Bloemfontein", 15.6),
                new WeatherStation(seed, "Boise", 11.4),
                new WeatherStation(seed, "Bordeaux", 14.2),
                new WeatherStation(seed, "Bosaso", 30.0),
                new WeatherStation(seed, "Boston", 10.9),
                new WeatherStation(seed, "Bouaké", 26.0),
                new WeatherStation(seed, "Bratislava", 10.5),
                new WeatherStation(seed, "Brazzaville", 25.0),
                new WeatherStation(seed, "Bridgetown", 27.0),
                new WeatherStation(seed, "Brisbane", 21.4),
                new WeatherStation(seed, "Brussels", 10.5),
                new WeatherStation(seed, "Bucharest", 10.8),
                new WeatherStation(seed, "Budapest", 11.3),
                new WeatherStation(seed, "Bujumbura", 23.8),
                new WeatherStation(seed, "Bulawayo", 18.9),
                new WeatherStation(seed, "Burnie", 13.1),
                new WeatherStation(seed, "Busan", 15.0),
                new WeatherStation(seed, "Cabo San Lucas", 23.9),
                new WeatherStation(seed, "Cairns", 25.0),
                new WeatherStation(seed, "Cairo", 21.4),
                new WeatherStation(seed, "Calgary", 4.4),
                new WeatherStation(seed, "Canberra", 13.1),
                new WeatherStation(seed, "Cape Town", 16.2),
                new WeatherStation(seed, "Changsha", 17.4),
                new WeatherStation(seed, "Charlotte", 16.1),
                new WeatherStation(seed, "Chiang Mai", 25.8),
                new WeatherStation(seed, "Chicago", 9.8),
                new WeatherStation(seed, "Chihuahua", 18.6),
                new WeatherStation(seed, "Chișinău", 10.2),
                new WeatherStation(seed, "Chittagong", 25.9),
                new WeatherStation(seed, "Chongqing", 18.6),
                new WeatherStation(seed, "Christchurch", 12.2),
                new WeatherStation(seed, "City of San Marino", 11.8),
                new WeatherStation(seed, "Colombo", 27.4),
                new WeatherStation(seed, "Columbus", 11.7),
                new WeatherStation(seed, "Conakry", 26.4),
                new WeatherStation(seed, "Copenhagen", 9.1),
                new WeatherStation(seed, "Cotonou", 27.2),
                new WeatherStation(seed, "Cracow", 9.3),
                new WeatherStation(seed, "Da Lat", 17.9),
                new WeatherStation(seed, "Da Nang", 25.8),
                new WeatherStation(seed, "Dakar", 24.0),
                new WeatherStation(seed, "Dallas", 19.0),
                new WeatherStation(seed, "Damascus", 17.0),
                new WeatherStation(seed, "Dampier", 26.4),
                new WeatherStation(seed, "Dar es Salaam", 25.8),
                new WeatherStation(seed, "Darwin", 27.6),
                new WeatherStation(seed, "Denpasar", 23.7),
                new WeatherStation(seed, "Denver", 10.4),
                new WeatherStation(seed, "Detroit", 10.0),
                new WeatherStation(seed, "Dhaka", 25.9),
                new WeatherStation(seed, "Dikson", -11.1),
                new WeatherStation(seed, "Dili", 26.6),
                new WeatherStation(seed, "Djibouti", 29.9),
                new WeatherStation(seed, "Dodoma", 22.7),
                new WeatherStation(seed, "Dolisie", 24.0),
                new WeatherStation(seed, "Douala", 26.7),
                new WeatherStation(seed, "Dubai", 26.9),
                new WeatherStation(seed, "Dublin", 9.8),
                new WeatherStation(seed, "Dunedin", 11.1),
                new WeatherStation(seed, "Durban", 20.6),
                new WeatherStation(seed, "Dushanbe", 14.7),
                new WeatherStation(seed, "Edinburgh", 9.3),
                new WeatherStation(seed, "Edmonton", 4.2),
                new WeatherStation(seed, "El Paso", 18.1),
                new WeatherStation(seed, "Entebbe", 21.0),
                new WeatherStation(seed, "Erbil", 19.5),
                new WeatherStation(seed, "Erzurum", 5.1),
                new WeatherStation(seed, "Fairbanks", -2.3),
                new WeatherStation(seed, "Fianarantsoa", 17.9),
                new WeatherStation(seed, "Flores,  Petén", 26.4),
                new WeatherStation(seed, "Frankfurt", 10.6),
                new WeatherStation(seed, "Fresno", 17.9),
                new WeatherStation(seed, "Fukuoka", 17.0),
                new WeatherStation(seed, "Gabès", 19.5),
                new WeatherStation(seed, "Gaborone", 21.0),
                new WeatherStation(seed, "Gagnoa", 26.0),
                new WeatherStation(seed, "Gangtok", 15.2),
                new WeatherStation(seed, "Garissa", 29.3),
                new WeatherStation(seed, "Garoua", 28.3),
                new WeatherStation(seed, "George Town", 27.9),
                new WeatherStation(seed, "Ghanzi", 21.4),
                new WeatherStation(seed, "Gjoa Haven", -14.4),
                new WeatherStation(seed, "Guadalajara", 20.9),
                new WeatherStation(seed, "Guangzhou", 22.4),
                new WeatherStation(seed, "Guatemala City", 20.4),
                new WeatherStation(seed, "Halifax", 7.5),
                new WeatherStation(seed, "Hamburg", 9.7),
                new WeatherStation(seed, "Hamilton", 13.8),
                new WeatherStation(seed, "Hanga Roa", 20.5),
                new WeatherStation(seed, "Hanoi", 23.6),
                new WeatherStation(seed, "Harare", 18.4),
                new WeatherStation(seed, "Harbin", 5.0),
                new WeatherStation(seed, "Hargeisa", 21.7),
                new WeatherStation(seed, "Hat Yai", 27.0),
                new WeatherStation(seed, "Havana", 25.2),
                new WeatherStation(seed, "Helsinki", 5.9),
                new WeatherStation(seed, "Heraklion", 18.9),
                new WeatherStation(seed, "Hiroshima", 16.3),
                new WeatherStation(seed, "Ho Chi Minh City", 27.4),
                new WeatherStation(seed, "Hobart", 12.7),
                new WeatherStation(seed, "Hong Kong", 23.3),
                new WeatherStation(seed, "Honiara", 26.5),
                new WeatherStation(seed, "Honolulu", 25.4),
                new WeatherStation(seed, "Houston", 20.8),
                new WeatherStation(seed, "Ifrane", 11.4),
                new WeatherStation(seed, "Indianapolis", 11.8),
                new WeatherStation(seed, "Iqaluit", -9.3),
                new WeatherStation(seed, "Irkutsk", 1.0),
                new WeatherStation(seed, "Istanbul", 13.9),
                new WeatherStation(seed, "İzmir", 17.9),
                new WeatherStation(seed, "Jacksonville", 20.3),
                new WeatherStation(seed, "Jakarta", 26.7),
                new WeatherStation(seed, "Jayapura", 27.0),
                new WeatherStation(seed, "Jerusalem", 18.3),
                new WeatherStation(seed, "Johannesburg", 15.5),
                new WeatherStation(seed, "Jos", 22.8),
                new WeatherStation(seed, "Juba", 27.8),
                new WeatherStation(seed, "Kabul", 12.1),
                new WeatherStation(seed, "Kampala", 20.0),
                new WeatherStation(seed, "Kandi", 27.7),
                new WeatherStation(seed, "Kankan", 26.5),
                new WeatherStation(seed, "Kano", 26.4),
                new WeatherStation(seed, "Kansas City", 12.5),
                new WeatherStation(seed, "Karachi", 26.0),
                new WeatherStation(seed, "Karonga", 24.4),
                new WeatherStation(seed, "Kathmandu", 18.3),
                new WeatherStation(seed, "Khartoum", 29.9),
                new WeatherStation(seed, "Kingston", 27.4),
                new WeatherStation(seed, "Kinshasa", 25.3),
                new WeatherStation(seed, "Kolkata", 26.7),
                new WeatherStation(seed, "Kuala Lumpur", 27.3),
                new WeatherStation(seed, "Kumasi", 26.0),
                new WeatherStation(seed, "Kunming", 15.7),
                new WeatherStation(seed, "Kuopio", 3.4),
                new WeatherStation(seed, "Kuwait City", 25.7),
                new WeatherStation(seed, "Kyiv", 8.4),
                new WeatherStation(seed, "Kyoto", 15.8),
                new WeatherStation(seed, "La Ceiba", 26.2),
                new WeatherStation(seed, "La Paz", 23.7),
                new WeatherStation(seed, "Lagos", 26.8),
                new WeatherStation(seed, "Lahore", 24.3),
                new WeatherStation(seed, "Lake Havasu City", 23.7),
                new WeatherStation(seed, "Lake Tekapo", 8.7),
                new WeatherStation(seed, "Las Palmas de Gran Canaria", 21.2),
                new WeatherStation(seed, "Las Vegas", 20.3),
                new WeatherStation(seed, "Launceston", 13.1),
                new WeatherStation(seed, "Lhasa", 7.6),
                new WeatherStation(seed, "Libreville", 25.9),
                new WeatherStation(seed, "Lisbon", 17.5),
                new WeatherStation(seed, "Livingstone", 21.8),
                new WeatherStation(seed, "Ljubljana", 10.9),
                new WeatherStation(seed, "Lodwar", 29.3),
                new WeatherStation(seed, "Lomé", 26.9),
                new WeatherStation(seed, "London", 11.3),
                new WeatherStation(seed, "Los Angeles", 18.6),
                new WeatherStation(seed, "Louisville", 13.9),
                new WeatherStation(seed, "Luanda", 25.8),
                new WeatherStation(seed, "Lubumbashi", 20.8),
                new WeatherStation(seed, "Lusaka", 19.9),
                new WeatherStation(seed, "Luxembourg City", 9.3),
                new WeatherStation(seed, "Lviv", 7.8),
                new WeatherStation(seed, "Lyon", 12.5),
                new WeatherStation(seed, "Madrid", 15.0),
                new WeatherStation(seed, "Mahajanga", 26.3),
                new WeatherStation(seed, "Makassar", 26.7),
                new WeatherStation(seed, "Makurdi", 26.0),
                new WeatherStation(seed, "Malabo", 26.3),
                new WeatherStation(seed, "Malé", 28.0),
                new WeatherStation(seed, "Managua", 27.3),
                new WeatherStation(seed, "Manama", 26.5),
                new WeatherStation(seed, "Mandalay", 28.0),
                new WeatherStation(seed, "Mango", 28.1),
                new WeatherStation(seed, "Manila", 28.4),
                new WeatherStation(seed, "Maputo", 22.8),
                new WeatherStation(seed, "Marrakesh", 19.6),
                new WeatherStation(seed, "Marseille", 15.8),
                new WeatherStation(seed, "Maun", 22.4),
                new WeatherStation(seed, "Medan", 26.5),
                new WeatherStation(seed, "Mek'ele", 22.7),
                new WeatherStation(seed, "Melbourne", 15.1),
                new WeatherStation(seed, "Memphis", 17.2),
                new WeatherStation(seed, "Mexicali", 23.1),
                new WeatherStation(seed, "Mexico City", 17.5),
                new WeatherStation(seed, "Miami", 24.9),
                new WeatherStation(seed, "Milan", 13.0),
                new WeatherStation(seed, "Milwaukee", 8.9),
                new WeatherStation(seed, "Minneapolis", 7.8),
                new WeatherStation(seed, "Minsk", 6.7),
                new WeatherStation(seed, "Mogadishu", 27.1),
                new WeatherStation(seed, "Mombasa", 26.3),
                new WeatherStation(seed, "Monaco", 16.4),
                new WeatherStation(seed, "Moncton", 6.1),
                new WeatherStation(seed, "Monterrey", 22.3),
                new WeatherStation(seed, "Montreal", 6.8),
                new WeatherStation(seed, "Moscow", 5.8),
                new WeatherStation(seed, "Mumbai", 27.1),
                new WeatherStation(seed, "Murmansk", 0.6),
                new WeatherStation(seed, "Muscat", 28.0),
                new WeatherStation(seed, "Mzuzu", 17.7),
                new WeatherStation(seed, "N'Djamena", 28.3),
                new WeatherStation(seed, "Naha", 23.1),
                new WeatherStation(seed, "Nairobi", 17.8),
                new WeatherStation(seed, "Nakhon Ratchasima", 27.3),
                new WeatherStation(seed, "Napier", 14.6),
                new WeatherStation(seed, "Napoli", 15.9),
                new WeatherStation(seed, "Nashville", 15.4),
                new WeatherStation(seed, "Nassau", 24.6),
                new WeatherStation(seed, "Ndola", 20.3),
                new WeatherStation(seed, "New Delhi", 25.0),
                new WeatherStation(seed, "New Orleans", 20.7),
                new WeatherStation(seed, "New York City", 12.9),
                new WeatherStation(seed, "Ngaoundéré", 22.0),
                new WeatherStation(seed, "Niamey", 29.3),
                new WeatherStation(seed, "Nicosia", 19.7),
                new WeatherStation(seed, "Niigata", 13.9),
                new WeatherStation(seed, "Nouadhibou", 21.3),
                new WeatherStation(seed, "Nouakchott", 25.7),
                new WeatherStation(seed, "Novosibirsk", 1.7),
                new WeatherStation(seed, "Nuuk", -1.4),
                new WeatherStation(seed, "Odesa", 10.7),
                new WeatherStation(seed, "Odienné", 26.0),
                new WeatherStation(seed, "Oklahoma City", 15.9),
                new WeatherStation(seed, "Omaha", 10.6),
                new WeatherStation(seed, "Oranjestad", 28.1),
                new WeatherStation(seed, "Oslo", 5.7),
                new WeatherStation(seed, "Ottawa", 6.6),
                new WeatherStation(seed, "Ouagadougou", 28.3),
                new WeatherStation(seed, "Ouahigouya", 28.6),
                new WeatherStation(seed, "Ouarzazate", 18.9),
                new WeatherStation(seed, "Oulu", 2.7),
                new WeatherStation(seed, "Palembang", 27.3),
                new WeatherStation(seed, "Palermo", 18.5),
                new WeatherStation(seed, "Palm Springs", 24.5),
                new WeatherStation(seed, "Palmerston North", 13.2),
                new WeatherStation(seed, "Panama City", 28.0),
                new WeatherStation(seed, "Parakou", 26.8),
                new WeatherStation(seed, "Paris", 12.3),
                new WeatherStation(seed, "Perth", 18.7),
                new WeatherStation(seed, "Petropavlovsk-Kamchatsky", 1.9),
                new WeatherStation(seed, "Philadelphia", 13.2),
                new WeatherStation(seed, "Phnom Penh", 28.3),
                new WeatherStation(seed, "Phoenix", 23.9),
                new WeatherStation(seed, "Pittsburgh", 10.8),
                new WeatherStation(seed, "Podgorica", 15.3),
                new WeatherStation(seed, "Pointe-Noire", 26.1),
                new WeatherStation(seed, "Pontianak", 27.7),
                new WeatherStation(seed, "Port Moresby", 26.9),
                new WeatherStation(seed, "Port Sudan", 28.4),
                new WeatherStation(seed, "Port Vila", 24.3),
                new WeatherStation(seed, "Port-Gentil", 26.0),
                new WeatherStation(seed, "Portland (OR)", 12.4),
                new WeatherStation(seed, "Porto", 15.7),
                new WeatherStation(seed, "Prague", 8.4),
                new WeatherStation(seed, "Praia", 24.4),
                new WeatherStation(seed, "Pretoria", 18.2),
                new WeatherStation(seed, "Pyongyang", 10.8),
                new WeatherStation(seed, "Rabat", 17.2),
                new WeatherStation(seed, "Rangpur", 24.4),
                new WeatherStation(seed, "Reggane", 28.3),
                new WeatherStation(seed, "Reykjavík", 4.3),
                new WeatherStation(seed, "Riga", 6.2),
                new WeatherStation(seed, "Riyadh", 26.0),
                new WeatherStation(seed, "Rome", 15.2),
                new WeatherStation(seed, "Roseau", 26.2),
                new WeatherStation(seed, "Rostov-on-Don", 9.9),
                new WeatherStation(seed, "Sacramento", 16.3),
                new WeatherStation(seed, "Saint Petersburg", 5.8),
                new WeatherStation(seed, "Saint-Pierre", 5.7),
                new WeatherStation(seed, "Salt Lake City", 11.6),
                new WeatherStation(seed, "San Antonio", 20.8),
                new WeatherStation(seed, "San Diego", 17.8),
                new WeatherStation(seed, "San Francisco", 14.6),
                new WeatherStation(seed, "San Jose", 16.4),
                new WeatherStation(seed, "San José", 22.6),
                new WeatherStation(seed, "San Juan", 27.2),
                new WeatherStation(seed, "San Salvador", 23.1),
                new WeatherStation(seed, "Sana'a", 20.0),
                new WeatherStation(seed, "Santo Domingo", 25.9),
                new WeatherStation(seed, "Sapporo", 8.9),
                new WeatherStation(seed, "Sarajevo", 10.1),
                new WeatherStation(seed, "Saskatoon", 3.3),
                new WeatherStation(seed, "Seattle", 11.3),
                new WeatherStation(seed, "Ségou", 28.0),
                new WeatherStation(seed, "Seoul", 12.5),
                new WeatherStation(seed, "Seville", 19.2),
                new WeatherStation(seed, "Shanghai", 16.7),
                new WeatherStation(seed, "Singapore", 27.0),
                new WeatherStation(seed, "Skopje", 12.4),
                new WeatherStation(seed, "Sochi", 14.2),
                new WeatherStation(seed, "Sofia", 10.6),
                new WeatherStation(seed, "Sokoto", 28.0),
                new WeatherStation(seed, "Split", 16.1),
                new WeatherStation(seed, "St. John's", 5.0),
                new WeatherStation(seed, "St. Louis", 13.9),
                new WeatherStation(seed, "Stockholm", 6.6),
                new WeatherStation(seed, "Surabaya", 27.1),
                new WeatherStation(seed, "Suva", 25.6),
                new WeatherStation(seed, "Suwałki", 7.2),
                new WeatherStation(seed, "Sydney", 17.7),
                new WeatherStation(seed, "Tabora", 23.0),
                new WeatherStation(seed, "Tabriz", 12.6),
                new WeatherStation(seed, "Taipei", 23.0),
                new WeatherStation(seed, "Tallinn", 6.4),
                new WeatherStation(seed, "Tamale", 27.9),
                new WeatherStation(seed, "Tamanrasset", 21.7),
                new WeatherStation(seed, "Tampa", 22.9),
                new WeatherStation(seed, "Tashkent", 14.8),
                new WeatherStation(seed, "Tauranga", 14.8),
                new WeatherStation(seed, "Tbilisi", 12.9),
                new WeatherStation(seed, "Tegucigalpa", 21.7),
                new WeatherStation(seed, "Tehran", 17.0),
                new WeatherStation(seed, "Tel Aviv", 20.0),
                new WeatherStation(seed, "Thessaloniki", 16.0),
                new WeatherStation(seed, "Thiès", 24.0),
                new WeatherStation(seed, "Tijuana", 17.8),
                new WeatherStation(seed, "Timbuktu", 28.0),
                new WeatherStation(seed, "Tirana", 15.2),
                new WeatherStation(seed, "Toamasina", 23.4),
                new WeatherStation(seed, "Tokyo", 15.4),
                new WeatherStation(seed, "Toliara", 24.1),
                new WeatherStation(seed, "Toluca", 12.4),
                new WeatherStation(seed, "Toronto", 9.4),
                new WeatherStation(seed, "Tripoli", 20.0),
                new WeatherStation(seed, "Tromsø", 2.9),
                new WeatherStation(seed, "Tucson", 20.9),
                new WeatherStation(seed, "Tunis", 18.4),
                new WeatherStation(seed, "Ulaanbaatar", -0.4),
                new WeatherStation(seed, "Upington", 20.4),
                new WeatherStation(seed, "Ürümqi", 7.4),
                new WeatherStation(seed, "Vaduz", 10.1),
                new WeatherStation(seed, "Valencia", 18.3),
                new WeatherStation(seed, "Valletta", 18.8),
                new WeatherStation(seed, "Vancouver", 10.4),
                new WeatherStation(seed, "Veracruz", 25.4),
                new WeatherStation(seed, "Vienna", 10.4),
                new WeatherStation(seed, "Vientiane", 25.9),
                new WeatherStation(seed, "Villahermosa", 27.1),
                new WeatherStation(seed, "Vilnius", 6.0),
                new WeatherStation(seed, "Virginia Beach", 15.8),
                new WeatherStation(seed, "Vladivostok", 4.9),
                new WeatherStation(seed, "Warsaw", 8.5),
                new WeatherStation(seed, "Washington, D.C.", 14.6),
                new WeatherStation(seed, "Wau", 27.8),
                new WeatherStation(seed, "Wellington", 12.9),
                new WeatherStation(seed, "Whitehorse", -0.1),
                new WeatherStation(seed, "Wichita", 13.9),
                new WeatherStation(seed, "Willemstad", 28.0),
                new WeatherStation(seed, "Winnipeg", 3.0),
                new WeatherStation(seed, "Wrocław", 9.6),
                new WeatherStation(seed, "Xi'an", 14.1),
                new WeatherStation(seed, "Yakutsk", -8.8),
                new WeatherStation(seed, "Yangon", 27.5),
                new WeatherStation(seed, "Yaoundé", 23.8),
                new WeatherStation(seed, "Yellowknife", -4.3),
                new WeatherStation(seed, "Yerevan", 12.4),
                new WeatherStation(seed, "Yinchuan", 9.0),
                new WeatherStation(seed, "Zagreb", 10.7),
                new WeatherStation(seed, "Zanzibar City", 26.0),
                new WeatherStation(seed, "Zürich", 9.3));

        Random random = new Random(seed);
        try (BufferedWriter bw = Files.newBufferedWriter(MEASUREMENT_FILE)) {
            for (int i = 0; i < size; i++) {
                if (i > 0 && i % 50_000_000 == 0) {
                    System.out.printf("Wrote %,d measurements in %s ms%n", i, System.currentTimeMillis() - start);
                }
                WeatherStation station = stations.get(random.nextInt(stations.size()));
                bw.write(station.id());
                bw.write(";");
                bw.write(Double.toString(station.measurement()));
                bw.write('\n');
            }
        }
        System.out.printf("Created file with %,d measurements in %s ms%n", size, System.currentTimeMillis() - start);
    }
}
