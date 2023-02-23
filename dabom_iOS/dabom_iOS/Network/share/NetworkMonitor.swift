//
//  NetworkMonitor.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import Foundation
import Network

final class NetworkMonitor{
    static let shared = NetworkMonitor()
    
    private let queue = DispatchQueue.global()
    private let monitor: NWPathMonitor
    public private(set) var isConnected:Bool = false
    public private(set) var connectionType:ConnectionType = .unknown
    
    enum ConnectionType {
        case wifi
        case cellular
        case ethernet
        case unknown
    }
    
    private init(){
        monitor = NWPathMonitor()
    }
    
    public func startMonitoring(){
        monitor.start(queue: queue)
        monitor.pathUpdateHandler = { [weak self] path in
            self?.isConnected = path.status == .satisfied
            self?.getConenctionType(path)
            
            if self?.isConnected == true{
                print("연결이된 상태임!")
            }else{
                print("연결 안된 상태임!")

            }
        }
    }
    
    public func stopMonitoring(){
        monitor.cancel()
    }
    
    
    private func getConenctionType(_ path:NWPath) {
        if path.usesInterfaceType(.wifi){
            connectionType = .wifi

        }else if path.usesInterfaceType(.cellular) {
            connectionType = .cellular

        }else if path.usesInterfaceType(.wiredEthernet) {
            connectionType = .ethernet

        }else {
            connectionType = .unknown
        }
    }
}
