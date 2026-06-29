package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiTotal20220304;
import com.sales.ops.db.entity.ShikomiTotal20220304Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiTotal20220304Mapper {
    long countByExample(ShikomiTotal20220304Example example);

    int deleteByExample(ShikomiTotal20220304Example example);

    int insert(ShikomiTotal20220304 record);

    int insertSelective(ShikomiTotal20220304 record);

    List<ShikomiTotal20220304> selectByExampleWithBLOBs(ShikomiTotal20220304Example example);

    List<ShikomiTotal20220304> selectByExample(ShikomiTotal20220304Example example);

    int updateByExampleSelective(@Param("record") ShikomiTotal20220304 record, @Param("example") ShikomiTotal20220304Example example);

    int updateByExampleWithBLOBs(@Param("record") ShikomiTotal20220304 record, @Param("example") ShikomiTotal20220304Example example);

    int updateByExample(@Param("record") ShikomiTotal20220304 record, @Param("example") ShikomiTotal20220304Example example);
}