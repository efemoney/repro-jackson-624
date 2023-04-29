package github624

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class TestGithub624 {

  private lateinit var mapper: ObjectMapper

  @BeforeEach
  fun setup() {
    mapper = jsonMapper {
      addModules(
        ParameterNamesModule(JsonCreator.Mode.DEFAULT),
        Github624Module,
        kotlinModule(),
      )
    }
  }

  @Test
  fun testAmount() {
    val result = mapper.parse("""{"amount" : 1500, "currency": "AED"}""")
    assertNotNull(result) {
      assert(it.amount == AmountInCents(1500))
      assert(it.currency == Currency.AED)
    }
  }

  @Test
  fun testAmountInCents() {
    val result = mapper.parse("""{"amountInCents" : 1500, "currency": "AED"}""")
    assertNotNull(result) {
      assert(it.amount == AmountInCents(1500))
      assert(it.currency == Currency.AED)
    }
  }

  @Test
  fun testUnspecifiedCurrency() {
    val result = mapper.parse("""{"amountInCents" : "0", "currency": ""}""")
    assertNotNull(result) {
      assert(it.amount == AmountInCents(0))
      assert(it.currency == Currency.Unspecified)
    }
  }
}

private fun ObjectMapper.parse(@Language("json") string: String): Savings = readValue<Savings>(string)
