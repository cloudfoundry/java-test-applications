package com.gopivotal.cloudfoundry.test.core;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedisUtils implements ServiceUtils<RedisConnectionFactory> {
    public String checkAccess(RedisConnectionFactory redisConnectionFactory) {
    		String errorMessage = validateRedisConnectionFactory(redisConnectionFactory);
    		
    		if (errorMessage != null) {
    			return errorMessage;
    		}
        try {
            RedisConnection connection = redisConnectionFactory.getConnection();
            String echoMessage = "hello";
            String reply = new String(connection.echo(echoMessage.getBytes()));
            if (reply.equals(echoMessage)) {
            		return "ok";
            } else {
            		return "redis connection failure: echo doesn't match the message";
            }
        } catch (Exception e) {
            return "failed with " + e.getMessage();
        }
    }

    public String getUrl(RedisConnectionFactory redisConnectionFactory) {
		String errorMessage = validateRedisConnectionFactory(redisConnectionFactory);
		
		if (errorMessage != null) {
			return errorMessage;
		}
    		String host = ((JedisConnectionFactory) redisConnectionFactory).getHostName();
    		int port = ((JedisConnectionFactory) redisConnectionFactory).getPort();
    		
    		return String.format("redis://%s:%d", host, port);
    }
    
    private String validateRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        if (redisConnectionFactory == null) {
            return "redis://no-RedisConnectionFactory-found";
        }
        if (!(redisConnectionFactory instanceof JedisConnectionFactory)) {
        		return "redis://RedisConnectionFactory-is-not-of-JedisConnectionFactory-type";
        }
        return null;
    }
    
    /**
     * Fake RedisConnectionFactory, since we don't have "in-memory" redis (equivalent to, say, H2 database)
     * 
     * Objects of this class can be used wherever auto-reconfiguration is expected to take place. If auto-reconfiguration
     * works as expected, they will be replaced with real connection factory object. If not, tests will fail as expected.
     */
    public static class FakeRedisConnectionFactory implements RedisConnectionFactory {
		@Override
		public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
			return null;
		}

		@Override
		public RedisConnection getConnection() {
			return null;
		}
	};
}
