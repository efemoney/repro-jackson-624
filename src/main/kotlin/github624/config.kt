package github624

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.Deserializers
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.databind.util.Converter
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

object Github624Module : SimpleModule("Github624Module") {
  override fun setupModule(context: SetupContext) {
    super.setupModule(context)
    context.addDeserializers(Deserializers624())
  }
}

private class Deserializers624 : Deserializers.Base() {
  override fun findBeanDeserializer(
    type: JavaType,
    config: DeserializationConfig?,
    beanDesc: BeanDescription?,
  ): JsonDeserializer<*>? {
    val kls = type.rawClass.kotlin
    return if (kls.isValue) ValueClassDeserializer(kls) else null
  }
}

private class ValueClassDeserializer<T : Any>(kls: KClass<T>) :
  StdDelegatingDeserializer<T>(ValueConstructorConverter(kls)) {
  override fun withDelegate(
    converter: Converter<Any, T>,
    delegateType: JavaType,
    delegateDeserializer: JsonDeserializer<*>,
  ) = StdDelegatingDeserializer(converter, delegateType, delegateDeserializer)
}

private class ValueConstructorConverter<T : Any>(private val kls: KClass<T>) : Converter<Any?, T> {
  private val ctor = kls.primaryConstructor!!
  private val paramType = ctor.parameters.single().type.javaType
  override fun convert(value: Any?): T = ctor.call(value)
  override fun getInputType(typeFactory: TypeFactory): JavaType = typeFactory.constructType(paramType)
  override fun getOutputType(typeFactory: TypeFactory): JavaType = typeFactory.constructType(kls.java)
}
