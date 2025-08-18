package winwin.data_api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import winwin.data_api.dto.ProcessDTO;

public class TransformationServiceTest {
    private final TransformationService transformationService = new TransformationService();

    @Test
    public void invokeTransformMethod_correct() {
        var input = "hello";
        var expected = "OLLEH";
        var output = transformationService.transform(new ProcessDTO(input)).getText();

        Assertions.assertThat(output).isEqualTo(expected);
    }
}
