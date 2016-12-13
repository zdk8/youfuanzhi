package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.workspace.dao.Ls_EarthMapper;
import cn.com.hvit.workspace.dao.Ls_ReservoirMapper;
import cn.com.hvit.workspace.dao.StationMapper;
import cn.com.hvit.workspace.service.IYiqiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */
@Service
public class YiqiServiceImpl implements IYiqiService {
    @Autowired
    private Ls_EarthMapper earthMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private Ls_ReservoirMapper reservoirMapper;

    @Override
    public List getEarthData() {
        return earthMapper.getEarthData();
    }

    @Override
    public List getStationData() {
        return stationMapper.getStation();
    }

    @Override
    public List getReservoirData() {
        return reservoirMapper.getReservior();
    }
}
