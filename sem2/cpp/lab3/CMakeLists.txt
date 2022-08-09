cmake_minimum_required(VERSION 3.22)
project(lab3)

set(CMAKE_CXX_STANDARD 17)

include_directories(.)

add_executable(lab3
        main.cpp phonebook.h
        return_codes.h  quicksort.h phonebook.cpp phonebook.h)
