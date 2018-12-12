package org.lily.micourse

import org.junit.Test
import org.junit.runner.RunWith
import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.po.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class MicourseApplicationTests {

    @Autowired
	lateinit var userRepository: UserRepository

	@Test
	fun contextLoads() {
        val user = User("abc@mail.com", "123456", "192.168.0.1",
                "abc.jpg", "不知道")

        userRepository.save(user)
	}

    @Test
    fun testFetch() {
        val user = userRepository.findByUsername("happy bird")
        println(user)
    }
}
