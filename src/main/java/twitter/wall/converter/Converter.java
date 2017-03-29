package twitter.wall.converter;

public interface Converter<INPUT, OUTPUT> {

	OUTPUT convert(INPUT input);

}
