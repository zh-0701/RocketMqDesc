/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.client.consumer.store;

import java.util.Map;
import java.util.Set;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 消费进度存储接口
 * Offset store interface
 */
public interface OffsetStore {

    //从文件中加载消费进度
    void load() throws MQClientException;

    //更新内存中的消费进度
    //offset 消息偏移量
    //increaseOnly true-只有offset大于当前内存偏移量才更新
    void updateOffset(final MessageQueue mq, final long offset, final boolean increaseOnly);

    //读取偏移量
    long readOffset(final MessageQueue mq, final ReadOffsetType type);

    //持久化指定消息至磁盘
    void persistAll(final Set<MessageQueue> mqs);

    /**
     * Persist the offset,may be in local storage or remote name server
     */
    void persist(final MessageQueue mq);

    //从内存中移除指定消费队列
    void removeOffset(MessageQueue mq);

    //克隆主题下所有消费队列
    Map<MessageQueue, Long> cloneOffsetTable(String topic);

    //集群模式下更新broker下消费进度
    void updateConsumeOffsetToBroker(MessageQueue mq, long offset, boolean isOneway) throws RemotingException,
            MQBrokerException, InterruptedException, MQClientException;
}
