package service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.FileNotFoundException;

import com.fasterxml.jackson.core.type.TypeReference;
import model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;


public class DataHandler {
     private final ObjectMapper objectMapper;
     private static final Properties properties = new Properties();
     private final String userFilePath;
     private final String taskFilePath;
     private final String categoryFilePath;
     private final String userToCategoryFilePath;
     private final String taskToTaskCatFilePath;


     static {
         try (InputStream inputStream = DataHandler.class.getClassLoader().getResourceAsStream("config.properties")) {
             if (inputStream != null) {
                 properties.load(inputStream);
             } else {
                 System.err.println("Properties file not found.");
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    // Checks if config loaded Properly
     private static boolean isConfigLoaded() {
         return !properties.isEmpty();
     }


     private void validateFile (File filepath) throws FileNotFoundException {
         if (!filepath.exists() || filepath.length() <= 0) {
             throw new FileNotFoundException("File does not exist or is empty");
         }
     }



    public DataHandler()  {
         this.objectMapper = new ObjectMapper();
         objectMapper.registerModule(new JavaTimeModule());
         objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
         objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

         String dataDir = properties.getProperty("data.directory");

         this.userFilePath = dataDir + File.separator + properties.getProperty("user.file");
         this.taskFilePath = dataDir + File.separator  + properties.getProperty("task.file");
         this.categoryFilePath = dataDir + File.separator  + properties.getProperty("taskCategory.file");
         this.userToCategoryFilePath = dataDir + File.separator  + properties.getProperty("userToCategory.file");
         this.taskToTaskCatFilePath = dataDir + File.separator  + properties.getProperty("taskToTaskCat.file");
     }


    public Map<String, User> loadUserData () throws IOException {
        File file = new File(userFilePath);
        validateFile(file);
        return objectMapper.readValue(file, new TypeReference<Map<String, User>>() {});
    }


    public Map<String, Task> loadTaskData () throws IOException {
         File file = new File(taskFilePath);
         validateFile(file);
         return objectMapper.readValue(file, new TypeReference<Map<String, Task>>() {});
    }

    public Map<String, TaskCategory> loadTaskCategoryData () throws IOException {
        File file = new File(categoryFilePath);
        validateFile(file);
        return objectMapper.readValue(file, new TypeReference<Map<String, TaskCategory>>() {});
    }

    public Map<String, String> loadUserToTaskCategoryData () throws IOException {
        File file = new File(userToCategoryFilePath);
        validateFile(file);
        return objectMapper.readValue(file, new TypeReference<Map<String, String>>() {});
    }

    public Map<String, String> loadTaskCategoryToTaskData () throws IOException {
        File file = new File(taskToTaskCatFilePath);
        validateFile(file);
        return objectMapper.readValue(file, new TypeReference<Map<String, String>>() {});
    }


    public void userJson(Map<String, User> hashMap) throws IOException {
        objectMapper.writeValue(new File(userFilePath), hashMap);
    }

    public void taskJson(Map<String, Task> hashMap)throws IOException {
        objectMapper.writeValue(new File(taskFilePath), hashMap);
    }

    public void categoryJson(Map<String, TaskCategory> hashMap)throws IOException {
        objectMapper.writeValue(new File(categoryFilePath), hashMap);
    }

    public void userToCategoryJson(Map<String, String> hashMap)throws IOException {
        objectMapper.writeValue(new File(userToCategoryFilePath), hashMap);
    }

    public void categoryToTaskJson(Map<String, String> hashMap)throws IOException {
        objectMapper.writeValue(new File(taskToTaskCatFilePath), hashMap);
    }


}
