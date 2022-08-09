cmake_minimum_required(VERSION 3.22)
project(lab4)

set(CMAKE_CXX_STANDARD 17)

include_directories(.)
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fsanitize=address -g")
add_executable(lab4
        LN.cpp
        LN.h
        return_codes.h
        main.cpp
        #arith.cpp
        )
