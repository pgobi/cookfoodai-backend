package com.pgobi.cookfood.ai.model;

import java.util.List;

public record ReceiptsResponse(
        List<Receipt> receipts
) {
}
