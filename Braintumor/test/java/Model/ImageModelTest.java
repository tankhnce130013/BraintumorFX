package Model;

import org.fpt.DTO.ImageDTO;
import org.fpt.Model.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageModelTest {
    ImageModel imageModel;
    @Test
    @DisplayName("Should load all image linked with task id")
    @RepeatedTest(50)
    void testLoadAllImagebyId() {
        boolean checkCorrect = true;
        boolean checkSize = false;
        String stringid = "29c37f9d-588e-4400-8277-0d5d3025802c";
        UUID id = UUID.fromString(stringid);
        ArrayList<ImageDTO> imgList = imageModel.loadAllImage(id);
        if (imgList.size()>0){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
        System.out.println("Success");
    }
    @BeforeEach
    void setUp() {
        imageModel = new ImageModel();
    }
}
