package com.rus.twitter.bot.service.impl;

import com.rus.twitter.bot.config.Data;
import com.rus.twitter.bot.service.TwitterBotMessageSender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class TwitterBotMessageSenderImpl implements TwitterBotMessageSender {

    Logger logger = LogManager.getLogger(TwitterBotMessageSenderImpl.class);

    @Autowired
    private Twitter twitter;


    @Override
    public void SendMessage(String message) {

        FilterQuery filtered = new FilterQuery();

        // if you enter keywords here it will filter, otherwise it will sample
        String keywords[] = {
                "@PoliticsPipo"
        };
        filtered.track(keywords);

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                if(!status.getUser().getScreenName().equalsIgnoreCase("PoliticsPipo") && Objects.nonNull(status.getText()) && status.getText().contains("Pipo")){
                    String tweetText= createText();
                    try {
                        System.out.println("@" + status.getUser().getScreenName() + " - " + tweetText);
                        StatusUpdate statusUpdate= new StatusUpdate("@"+status.getUser().getScreenName() +" "+ tweetText);
                        statusUpdate.setInReplyToStatusId(status.getId());
                        status = twitter.updateStatus(statusUpdate);
                        System.out.println(status);

                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    logger.info("Sending Message To Twitter");


                }
            }

            private String createText() {


                String element1 = Data.Pipo.LIST_1.get(new Random().nextInt(Data.Pipo.LIST_1.size()) );
                String element2 = Data.Pipo.LIST_2.get(new Random().nextInt(Data.Pipo.LIST_2.size()) );
                String element3 = Data.Pipo.LIST_3.get(new Random().nextInt(Data.Pipo.LIST_3.size() ) );
                String element4 = Data.Pipo.LIST_4.get(new Random().nextInt(Data.Pipo.LIST_4.size() ) );
                String element5 = Data.Pipo.LIST_5.get(new Random().nextInt(Data.Pipo.LIST_5.size() ) );
                String element6 = Data.Pipo.LIST_6.get(new Random().nextInt(Data.Pipo.LIST_6.size() ) );
                String element7 = Data.Pipo.LIST_7.get(new Random().nextInt(Data.Pipo.LIST_7.size() ) );
                String element8 = Data.Pipo.LIST_8.get(new Random().nextInt(Data.Pipo.LIST_8.size()) );
                String element9 = Data.Pipo.LIST_9.get(new Random().nextInt(Data.Pipo.LIST_9.size() ) );

                String tweet =element1 +" "+ element2 +" "+ element3 +", "+ element4;
                if(tweet.endsWith("#") && ( element5.startsWith("a") ||element5.startsWith("e")||element5.startsWith("i")
                        ||element5.startsWith("o") ||element5.startsWith("u") ||element5.startsWith("y"))){
                    tweet =tweet.replace("#", "'");
                    tweet =tweet+element5;
                }else {
                    tweet =tweet.replace("#", "e");
                    tweet =tweet+" "+element5;
                }

                tweet=tweet +" "+element6+" "+element7+" "+element8+" "+element9;
                return tweet;
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        }).filter(filtered);




    Status status;
        /*
            User user = twitter.verifyCredentials();
            logger.error(user);
            logger.error(user.getScreenName());


            List<Status> statuses = twitter.getMentionsTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            for (Status s : statuses) {
                System.out.println("@" + s.getUser().getScreenName() + " - " + s.getText());
            }*/

            //status = twitter.updateStatus(message);
            logger.info("Sending Message To Twitter");
            //System.out.println(status);
    }
}
