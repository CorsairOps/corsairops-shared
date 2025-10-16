package com.corsairops.shared.dto.asset;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssetLocationResponse(
        Long id,
        UUID assetId,
        Double longitude,
        Double latitude,
        LocalDateTime timestamp
) {

}