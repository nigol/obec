package cz.nigol.obec.services.impl;

import java.util.*;
import java.util.stream.Collectors;
import javax.inject.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;
import cz.nigol.obec.config.*;

@Stateless
public class UserServiceImpl implements UserService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;
    @Inject
    private MailService mailService;

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.GET_ALL, User.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public User saveUser(User user) {
        return em.merge(user);
    }

    @Override
    public List<User> getActiveUsers() {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.GET_ACTIVE, User.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public User getActiveUserByUserId(String userId) {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.GET_ACTIVE_BY_USER_ID, User.class);
        typedQuery.setParameter(User.USER_ID_PARAM, userId);
        List<User> users = typedQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> typedQuery = em.createNamedQuery(Role.GET_ALL, Role.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public Role saveRole(Role role) {
        return em.merge(role);
    }

    @Override
    public void deleteRole(Role role) {
        em.remove(em.merge(role));
    }

    @Override
    public Role getRoleById(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Path> initializePaths(List<String> paths) {
        return paths.stream()
            .map(this::createPath)
            .collect(Collectors.toList());
    }

    private Path createPath(String path) {
        return em.merge(new Path(path));
    }

    @Override
    public Path getPathById(String id) {
        return em.find(Path.class, id);
    }

    @Override
    public Role saveRole(Role role, List<Path> paths) {
        Role roleInContext = em.merge(role);
        roleInContext.setPaths(new HashSet<>());
        roleInContext.getPaths().addAll(paths);
        return roleInContext;
    }

    @Override
    public User getUserByUserId(String userId) {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.GET_BY_USER_ID, User.class);
        typedQuery.setParameter(User.USER_ID_PARAM, userId);
        List<User> users = typedQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findUsersByFullName(String name) {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.FIND_ACTIVE_USER_NAME, User.class);
        typedQuery.setParameter(User.NAME_PARAM, "%" + name + "%");
        return typedQuery.getResultList();
    }

    @Override
    public void unsubscribeAnnouncements(long id) {
        User user = getUserById(id);
        user.setSendAnnouncements(false);
    }

    @Override
    public List<User> getAnnouncementSubscribers() {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.FIND_ANNOUNCEMENT_SUBSCRIBERS, User.class);
        return typedQuery.getResultList();
    }

    @Override
    public void subscribeAnnouncements(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setUserId(email);
            user.setEmail(email);
            user = saveUser(user);
        }
        user.setSendAnnouncements(true);
    }

    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.GET_BY_EMAIL, 
                User.class);
        typedQuery.setParameter(User.EMAIL_PARAM, email.toLowerCase());
        List<User> users = typedQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    @Asynchronous
    public void sendPasswordLinkByEmail(String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            String body = Templates.PASS_RESET
                .replaceAll("VARIABLE1", generateStoreToken(user));
            mailService.sendEmail(user.getEmail(), Templates.PASS_RESET_SUBJ, 
                    body);
        }
    }

    private String generateStoreToken(User user) {
        String token = "" + user.getId() + new Date();
        token = BCrypt.hashpw(token, BCrypt.gensalt());
        token = Base64.getEncoder().encodeToString(token.getBytes());
        user.setToken(token);
        return token;
    }

    @Override
    public User getUserByToken(String token) {
        TypedQuery<User> typedQuery = em.createNamedQuery(User.GET_BY_TOKEN, 
                User.class);
        typedQuery.setParameter(User.TOKEN_PARAM, token);
        List<User> users = typedQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

	@Override
	@Asynchronous
	public void sendNewUserInfoByEmail(String email, User user) {
		if (user != null) {
			String body = Templates.NEW_USER
			.replaceAll("VARIABLE1", user.getEmail());
			mailService.sendEmail(email, Templates.NEW_USER_SUBJ, body);
		}
	}
}
