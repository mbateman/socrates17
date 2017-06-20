/**
 * Created by michael on 17/06/2017.
 */
import org.junit.Assert.assertEquals
import org.junit.Test

class PeopleRegisterTest {

    val people = """Adam 18
Bob 15
Charlie 8
Dave 49
Elizabeth 12
Francis 42
George 85
Harriet 62
Janet 13
Karl 8
Laura 6
Mary 19
Nick 16
Oliver 54
Peter 27
Peter 34
Robert 36
Sally 26
Tom 58
Tom 13
Tom 58
Tom 45
Vicky 60
Wally 28"""


    val register = PeopleRegisterParser.parse(people)

    @Test fun should_report_twenty_four_people() {
        assertEquals(24, register.count());
    }

    @Test fun should_say_that_Dave_is_the_oldest() {
        assertEquals(Person("George", 85), register.oldestPerson()!!)
    }

    @Test fun should_say_that_Tom_is_the_commonest_name() {
        assertEquals("Tom", register.commonestName())
    }

    @Test fun should_say_that_the_youngest_Peter_is_27_years_old() {
        assertEquals(27, register.youngestCalled("Peter"))
    }

    @Test fun should_report_eight_children() {
        assertEquals(8, register.countOfChildren())
    }

    @Test fun should_report_an_adult_to_child_ratio_of_2() {
        assertEquals(2.0, register.adultToChildRatio(), 0.001)
    }

    @Test fun should_calculate_the_average_age() {
        assertEquals(33.0, register.averageAge(), 0.001)
    }

    @Test fun should_calculate_the_median_age() {
        assertEquals(27.5, register.medianAge(), 0.001)
    }

}

class PeopleRegister(val people: List<Person>) {

    fun count(): Int = people.count()

    fun oldestPerson(): Person? = people.maxBy { it.age }

    fun commonestName(): String? = people.groupingBy { it.name }.eachCount().maxBy { it.value }?.key

    fun youngestCalled(sought: String): Int? = people.filter { it.name == sought }.minBy { it.age }?.age

    fun countOfChildren(): Int = people.filter{it.age < 18}.count()

    fun countOfAdults(): Int = people.filter{it.age >= 18}.count()

    fun adultToChildRatio(): Double = (countOfAdults() / countOfChildren()).toDouble()

    fun averageAge(): Double = people.sumByDouble { it.age.toDouble() } / people.count()

    fun medianAge(): Double {
        val isEven = people.size % 2 == 0
        val sorted: List<Person> = people.sortedBy { it.age }
        if (isEven) {
            val val1 = sorted.get((people.size / 2) - 1).age
            val val2 = sorted.get((people.size / 2)).age
            return (val1 + val2) / 2.0
        } else {
            return sorted.get(people.size / 2).age.toDouble()
        }
    }

}


data class Person(val name: String, val age: Int)

object PersonParser {
    fun parse(data: String): Person {
        val params = data.trim().split(" ")
        return Person(params.get(0), params.get(1).toInt())
    }
}

object PeopleRegisterParser {
    fun parse(personData: String): PeopleRegister = PeopleRegister(personData.trim().split("\n").map{ s -> PersonParser.parse(s)})
}
