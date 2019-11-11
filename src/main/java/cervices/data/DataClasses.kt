package cervices.data

import entities.PeopleGroup
import enums.QueryCommandType
import enums.Resolution


data class QueryCommand(val type: QueryCommandType, val peopleGroup: PeopleGroup)

data class OrderResolution(val peopleGroup: PeopleGroup, val resolution: Resolution)