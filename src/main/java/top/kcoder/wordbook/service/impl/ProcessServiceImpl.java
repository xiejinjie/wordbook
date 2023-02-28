package top.kcoder.wordbook.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.kcoder.wordbook.service.IProcessService;
import top.kcoder.wordbook.util.CommonThreadPool;
import top.kcoder.wordbook.util.CommonUtil;

import java.io.*;
import java.util.concurrent.Future;

/**
 * CmdServiceImpl
 *
 * @author xiejinjie
 * @date 2022/12/22
 */
@Service
public class ProcessServiceImpl implements IProcessService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
    

    @Override
    public Process exec(String[] cmd) {
        String cmdStr = String.join(" ", cmd);
        logger.info("Execute a command: {}", cmdStr);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            return processBuilder.start();
        } catch (IOException e) {
            logger.error("Command execute failed! cmd = " + cmdStr, e);
            return null;
        }
    }

    @Override
    public boolean execSync(String[] cmd) {
        return execSync(cmd, null);
    }

    @Override
    public boolean execSync(String[] cmd, String directory) {
        String cmdStr = String.join(" ", cmd);
        logger.info("Execute a command: {}", cmdStr);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            if (CommonUtil.isNotEmpty(directory)) {
                processBuilder.directory(new File(directory));
            }
            Process pro = processBuilder.start();
            try (InputStream in = pro.getInputStream();
                 BufferedReader read = new BufferedReader(new InputStreamReader(in, "gbk"))) {
                String line = null;
                while ((line = read.readLine()) != null) {
                    logger.info(line);
                }
            }
            int code = pro.waitFor();
            logger.info("Command exit with code {}.", code);
            return code == 0;
        } catch (IOException e) {
            logger.error("Command execute failed! cmd = " + cmdStr, e);
        } catch (InterruptedException e) {
            logger.warn("Command interrupted! cmd = " + cmdStr, e);
        }
        return false;
    }

    @Override
    public Future<Boolean> execAsync(String[] cmd) {
        return CommonThreadPool.submit(() -> execSync(cmd, null));
    }

    @Override
    public String[] buildCmd(String cmd) {
        return new String[]{"cmd", "/c", cmd};
    }
}
