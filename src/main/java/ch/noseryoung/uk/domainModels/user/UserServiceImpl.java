package ch.noseryoung.uk.domainModels.user;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// This is an example service implementation with coded out CRUD logic
// Note that the @Service annotation belongs on here as the effective logic is found here
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuctionRepository auctionRepository) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
    }

    // The logic for creating a new user
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    // The logic for retrieving all users
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // The logic for retrieving a single user with a given id
    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    // The logic for updating an existing user with a given id and data
    @Override
    public User updateById(int id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);

/*
            List<Auction> auctions = user.getAuctions().stream().map(auction -> {
                if (auctionRepository.existsById(auction.getId())) {
                    return auctionRepository.findById(auction.getId()).get();
                } else {
                    auctionRepository.save(auction);
                }
                return auction;
            }).collect(Collectors.toList());
            user.setAuctions(auctions);
            userRepository.save(user);
            return user;*/
        } else {
            throw new NoSuchElementException("No value present");
        }
        return null;
    }

    // The logic for deleting a user with a given id
    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
