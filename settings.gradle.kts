rootProject.name = "ImprovedScoreboard"

include(":bukkit")
include(":api")
include(":common")
include(":v1_21")
project(":v1_21").projectDir = file("versions/v1_21")
include(":v1_20_6")
project(":v1_20_6").projectDir = file("versions/v1_20_6")
include(":v1_20_4")
project(":v1_20_4").projectDir = file("versions/v1_20_4")
include(":v1_20_2")
project(":v1_20_2").projectDir = file("versions/v1_20_2")
include(":v1_20_1")
project(":v1_20_1").projectDir = file("versions/v1_20_1")