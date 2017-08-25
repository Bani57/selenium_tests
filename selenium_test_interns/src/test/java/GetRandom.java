import com.fasterxml.jackson.databind.ObjectMapper;
import model.Project;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

class GetRandom {
    private Random random;
    private List<User> userList;
    private List<Project> projectList;

    GetRandom() throws IOException{
        random=new Random();
        byte[] jsonData = Files.readAllBytes(Paths.get("src\\main\\resources\\users"));
        ObjectMapper objectMapper = new ObjectMapper();
        userList = objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
        jsonData = Files.readAllBytes(Paths.get("src\\main\\resources\\projects"));
        objectMapper = new ObjectMapper();
        projectList = objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, Project.class));
    }

    User getRandomUser() throws IOException
    {
        int index=random.nextInt(userList.size());
        return userList.get(index);
    }
    Project getRandomProject() throws IOException
    {

        int index=random.nextInt(projectList.size());
        return projectList.get(index);
    }
}
