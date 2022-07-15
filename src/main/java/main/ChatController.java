package main;

import java.util.HashMap;
import java.util.List;
import main.model.User;
import main.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class ChatController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/init")
  public HashMap<String, Boolean> init() {
    //TODO: check sessionId. If found => true, if not => false
    HashMap<String, Boolean> response = new HashMap<>();
    response.put("result", false);
    return response;
  }

  @PostMapping("/auth")
  public HashMap<String, Boolean> auth(@RequestParam String name) {
    //TODO: create user with name ,sessionId
    // save user DB

    HashMap<String, Boolean> response = new HashMap<>();

    String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
    User user = new User();
    user.setName(name);
    user.setSessionId(sessionId);
    userRepository.save(user);

    response.put("result", true);
    return response;
  }

  @PostMapping("/message")
  public boolean sendMessage(@RequestParam String message) {
    return true;
  }

  @GetMapping("/message")
  public List<String> getMessagesList() {
    return null;
  }

  @GetMapping("/user")
  public HashMap<Integer, String> getUsersList() {
    return null;
  }
}
