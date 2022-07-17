package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import main.dto.DtoMessage;
import main.dto.MessageMapper;
import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class ChatController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MessageRepository messageRepository;


  private MessageMapper messageMapper;

  @GetMapping("/init")
  public HashMap<String, Boolean> init() {
    //TODO: check sessionId. If found => true, if not => false
    HashMap<String, Boolean> response = new HashMap<>();
    String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
    Optional<User> userOptional = userRepository.findBySessionId(sessionId);
    response.put("result", userOptional.isPresent());
    return response;
  }

  @PostMapping("/auth")
  public HashMap<String, Boolean> auth(@RequestParam String name) {
    //TODO: create user with name ,sessionId
    // save user DB
    HashMap<String, Boolean> response = new HashMap<>();

    if (Strings.isEmpty(name)) {
      response.put("result", false);
      return response;
    }
    String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
    User user = new User();
    user.setName(name);
    user.setSessionId(sessionId);
    userRepository.save(user);

    response.put("result", true);
    return response;
  }

  @PostMapping("/message")
  public Map<Object, Object> sendMessage(@RequestParam String message) {

    if (Strings.isEmpty(message)) {
      return Map.of("result", false);
    }

    String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
    User user = userRepository.findBySessionId(sessionId).get();
    Message msg = new Message();
    msg.setLocalDateTime(LocalDateTime.now());
    msg.setMessage(message);
    msg.setUser(user);
    messageRepository.save(msg);
    return Map.of("result", true);
  }


  @GetMapping("/message")
  public List<DtoMessage> getMessagesList() {

    return messageRepository
        .findAll(Sort.by(Sort.Direction.ASC,"localDateTime"))
        .stream()
        .map(message -> MessageMapper.map(message))
        .collect(Collectors.toList());
  }

  @GetMapping("/user")
  public HashMap<Integer, String> getUsersList() {
    return null;
  }
}
